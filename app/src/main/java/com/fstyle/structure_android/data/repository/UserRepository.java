package com.fstyle.structure_android.data.repository;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import com.fstyle.structure_android.data.model.User;
import com.fstyle.structure_android.data.source.UserDataSource;
import com.fstyle.structure_android.data.source.local.UserLocalDataSource;
import com.fstyle.structure_android.data.source.remote.UserRemoteDataSource;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by le.quang.dao on 10/03/2017.
 */

public class UserRepository
        implements UserDataSource.RemoteDataSource, UserDataSource.LocalDataSource {

    private static UserRepository instance;

    @NonNull
    private UserLocalDataSource mUserLocalDataSource;

    @NonNull
    private UserRemoteDataSource mUserRemoteDataSource;

    /**
     * This variable has package local visibility so it can be accessed from tests.
     */
    @VisibleForTesting
    @Nullable
    private List<User> mCachedUsers;

    /**
     * Marks the cache as invalid, to force an update the next time data is requested. This variable
     * has package local visibility so it can be accessed from tests.
     */
    @VisibleForTesting
    boolean mCacheIsDirty = false;

    private UserRepository(@NonNull UserLocalDataSource userLocalDataSource,
            @NonNull UserRemoteDataSource userRemoteDataSource) {
        mUserLocalDataSource = checkNotNull(userLocalDataSource);
        mUserRemoteDataSource = checkNotNull(userRemoteDataSource);
    }

    /**
     * Returns the single instance of this class, creating it if necessary.
     *
     * @param remoteDataSource the backend data source
     * @param localDataSource the device storage data source
     * @return the {@link UserRepository} instance
     */
    public static synchronized UserRepository getInstance(
            @NonNull UserLocalDataSource localDataSource,
            @NonNull UserRemoteDataSource remoteDataSource) {
        if (instance == null) {
            instance = new UserRepository(checkNotNull(localDataSource),
                    checkNotNull(remoteDataSource));
        }
        return instance;
    }

    /**
     * Used to force {@link #getInstance(UserLocalDataSource, UserRemoteDataSource)} to create a new
     * instance
     * next time it's called.
     */
    public static void destroyInstance() {
        instance = null;
    }

    @Override
    public Completable insertUser(@NonNull User user) {
        return mUserLocalDataSource.insertUser(checkNotNull(user)).doOnComplete(() -> {
            if (mCachedUsers != null) {
                mCachedUsers.add(user);
            }
        });
    }

    @Override
    public Completable updateUser(@NonNull User user) {
        return mUserLocalDataSource.updateUser(checkNotNull(user)).doOnComplete(() -> {
            if (mCachedUsers != null) {
                for (User cachedUser : mCachedUsers) {
                    if (cachedUser.getAvatarUrl().equals(user.getAvatarUrl())) {
                        cachedUser.setLogin(user.getLogin());
                        break;
                    }
                }
            }
        });
    }

    @Override
    public Completable deleteUser(@NonNull User user) {
        return mUserLocalDataSource.deleteUser(checkNotNull(user)).doOnComplete(() -> {
            if (mCachedUsers != null) {
                for (User cachedUser : mCachedUsers) {
                    if (cachedUser.getAvatarUrl().equals(user.getAvatarUrl())) {
                        mCachedUsers.remove(cachedUser);
                        break;
                    }
                }
            }
        });
    }

    @Override
    public Completable insertOrUpdateUser(@NonNull User user) {
        return mUserLocalDataSource.insertOrUpdateUser(checkNotNull(user)).doOnComplete(() -> {
            if (mCachedUsers != null) {
                for (User cachedUser : mCachedUsers) {
                    if (cachedUser.getAvatarUrl().equals(user.getAvatarUrl())) {
                        cachedUser.setLogin(user.getLogin());
                        break;
                    }
                }
            }
        });
    }

    /**
     * Gets users from cache, local data source (SQLite) or remote data source, whichever is
     * available first.
     */
    @Override
    public Flowable<List<User>> getUsers() {
        if (mCachedUsers != null && !mCacheIsDirty) {
            return Flowable.fromIterable(mCachedUsers).toList().toFlowable();
        } else if (mCachedUsers == null) {
            mCachedUsers = new ArrayList<>();
        }

        Flowable<List<User>> remoteUsers = getAndSaveRemoteUsers();

        if (mCacheIsDirty) {
            return remoteUsers;
        } else {
            // Query the local storage if available. If not, query the network.
            Flowable<List<User>> localUsers = getAndCacheLocalTasks();
            return Flowable.concat(localUsers, remoteUsers).firstElement().toFlowable();
        }
    }

    @Override
    public Flowable<User> getUser(String userLogin) {
        return mUserLocalDataSource.getUser(userLogin);
    }

    @Override
    public Completable deleteAllUsers() {
        return mUserLocalDataSource.deleteAllUsers().doOnComplete(() -> mCachedUsers = null);
    }

    public void refresh() {
        mCacheIsDirty = true;
        mCachedUsers = null;
    }

    private Flowable<List<User>> getAndSaveRemoteUsers() {
        return mUserRemoteDataSource.getUsers().flatMap(users -> {
            mUserLocalDataSource.deleteAllUsers().subscribe();
            return Flowable.fromIterable(users).doOnNext(user -> {
                mUserLocalDataSource.insertUser(user).subscribe();
                if (mCachedUsers == null) {
                    mCachedUsers = new ArrayList<>();
                }
                mCachedUsers.add(user);
            });
        }).toList().toFlowable().doOnComplete(() -> mCacheIsDirty = false);
    }

    private Flowable<List<User>> getAndCacheLocalTasks() {
        return mUserLocalDataSource.getUsers()
                .flatMap(users -> Flowable.fromIterable(users).doOnNext(user -> {
                    if (mCachedUsers == null) {
                        mCachedUsers = new ArrayList<>();
                    }
                    mCachedUsers.add(user);
                }).toList().toFlowable());
    }

    @Override
    public Flowable<List<User>> searchUsers(String userName) {
        return mUserLocalDataSource.searchUsers(userName);
    }
}
