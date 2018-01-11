package com.fstyle.structure_android.data.repository;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import com.fstyle.structure_android.data.model.User;
import com.fstyle.structure_android.data.source.local.UserLocalDataSource;
import com.fstyle.structure_android.data.source.remote.UserRemoteDataSource;
import com.fstyle.structure_android.data.source.UserDataSource;
import io.reactivex.Completable;
import io.reactivex.Maybe;
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

    public UserRepository(@NonNull UserLocalDataSource userLocalDataSource,
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
    public Completable insertListUser(List<User> users) {
        return mUserLocalDataSource.insertListUser(checkNotNull(users));
    }

    @Override
    public Completable insertUser(@NonNull User user) {
        return mUserLocalDataSource.insertUser(checkNotNull(user));
    }

    @Override
    public Completable updateUser(@NonNull User user) {
        return mUserLocalDataSource.updateUser(checkNotNull(user));
    }

    @Override
    public Completable deleteUser(@NonNull User user) {
        return mUserLocalDataSource.deleteUser(checkNotNull(user));
    }

    @Override
    public Completable insertOrUpdateUser(@NonNull User user) {
        return mUserLocalDataSource.insertOrUpdateUser(checkNotNull(user));
    }

    /**
     * Gets users from cache, local data source (SQLite) or remote data source, whichever is
     * available first.
     */
    @Override
    public Maybe<List<User>> getAllUser() {
        return mUserRemoteDataSource.getAllUser();
    }

    @Override
    public Maybe<User> findUserByUserLogin(String userLogin) {
        return mUserLocalDataSource.findUserByUserLogin(userLogin);
    }

    @Override
    public Completable deleteAllUsers() {
        return mUserLocalDataSource.deleteAllUsers();
    }
}
