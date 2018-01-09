package com.fstyle.structure_android.data.repository;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.fstyle.structure_android.data.model.User;
import com.fstyle.structure_android.data.source.remote_api.response.SearchUserResponse;
import com.fstyle.structure_android.data.source.UserDataSource;
import com.fstyle.structure_android.data.source.local.sqlite.UserDbHelper;
import com.fstyle.structure_android.data.source.remote_api.service.NameApi;

import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableOnSubscribe;
import io.reactivex.CompletableSource;
import io.reactivex.Maybe;
import io.reactivex.MaybeEmitter;
import io.reactivex.MaybeOnSubscribe;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import java.util.concurrent.Callable;

/**
 * Created by le.quang.dao on 10/03/2017.
 */

public class UserRepository
        implements UserDataSource.RemoteDataSource, UserDataSource.LocalDataSource {

    private static final String SELECT_ALL_USER_QUERY =
            String.format("SELECT * FROM %s", UserDbHelper.UserEntry.TABLE_NAME);

    private NameApi mNameApi;
    private SQLiteDatabase mDatabase;

    public UserRepository(SQLiteDatabase database, NameApi api) {
        mDatabase = database;
        mNameApi = api;
    }

    @Override
    public Single<List<User>> searchUsers(String keyWord, String limit) {
        return mNameApi.searchGithubUsers(keyWord, limit)
                .flatMap(new Function<SearchUserResponse, SingleSource<? extends List<User>>>() {
                    @Override
                    public SingleSource<? extends List<User>> apply(
                            SearchUserResponse searchUserResponse) throws Exception {
                        return Single.just(searchUserResponse.getItems());
                    }
                });
    }

    @Override
    public Completable insertUser(@NonNull final User user) {
        mDatabase.beginTransaction();
        return Completable.create(new CompletableOnSubscribe() {
            @Override
            public void subscribe(CompletableEmitter emitter) throws Exception {
                try {
                    ContentValues values = new ContentValues();
                    values.put(UserDbHelper.UserEntry.COLUMN_NAME_USER_LOGIN, user.getLogin());
                    values.put(UserDbHelper.UserEntry.COLUMN_NAME_AVATAR_URL, user.getAvatarUrl());
                    values.put(UserDbHelper.UserEntry.COLUMN_NAME_SUBSCRIPTIONS_URL,
                            user.getSubscriptionsUrl());
                    mDatabase.insert(UserDbHelper.UserEntry.TABLE_NAME, null, values);
                    mDatabase.close();
                    emitter.onComplete();
                } catch (RuntimeException e) {
                    emitter.onError(e);
                }
            }
        });
    }

    @Override
    public Completable updateUser(@NonNull final User user) {
        mDatabase.beginTransaction();
        return Completable.create(new CompletableOnSubscribe() {
            @Override
            public void subscribe(CompletableEmitter emitter) throws Exception {
                try {
                    ContentValues values = new ContentValues();
                    values.put(UserDbHelper.UserEntry.COLUMN_NAME_USER_LOGIN, user.getLogin());
                    values.put(UserDbHelper.UserEntry.COLUMN_NAME_AVATAR_URL, user.getAvatarUrl());
                    values.put(UserDbHelper.UserEntry.COLUMN_NAME_SUBSCRIPTIONS_URL,
                            user.getSubscriptionsUrl());
                    mDatabase.update(UserDbHelper.UserEntry.TABLE_NAME, values,
                            UserDbHelper.UserEntry.COLUMN_NAME_USER_LOGIN + "= ?",
                            new String[] { user.getLogin() });
                    mDatabase.close();
                    emitter.onComplete();
                } catch (RuntimeException e) {
                    emitter.onError(e);
                }
            }
        });
    }

    @Override
    public Completable deleteUser(@NonNull final User user) {
        Observable.create(new ObservableOnSubscribe<Void>() {
            @Override
            public void subscribe(ObservableEmitter<Void> e) throws Exception {

            }
        });
        mDatabase.beginTransaction();
        return Completable.create(new CompletableOnSubscribe() {
            @Override
            public void subscribe(CompletableEmitter emitter) throws Exception {
                try {
                    final String selection =
                            UserDbHelper.UserEntry.COLUMN_NAME_USER_LOGIN + " LIKE ?";
                    final String[] selectionArgs = { user.getLogin() };
                    mDatabase.delete(UserDbHelper.UserEntry.TABLE_NAME, selection, selectionArgs);
                    mDatabase.close();
                    emitter.onComplete();
                } catch (RuntimeException e) {
                    emitter.onError(e);
                }
            }
        });
    }

    @Override
    public Completable insertOrUpdateUser(@NonNull final User user) {
        if (!mDatabase.isOpen()) {
            mDatabase.beginTransaction();
        }
        return Completable.create(new CompletableOnSubscribe() {
            @Override
            public void subscribe(CompletableEmitter emitter) throws Exception {
                try {
                    ContentValues values = new ContentValues();
                    values.put(UserDbHelper.UserEntry.COLUMN_NAME_USER_LOGIN, user.getLogin());
                    values.put(UserDbHelper.UserEntry.COLUMN_NAME_AVATAR_URL, user.getAvatarUrl());
                    values.put(UserDbHelper.UserEntry.COLUMN_NAME_SUBSCRIPTIONS_URL,
                            user.getSubscriptionsUrl());
                    mDatabase.insertWithOnConflict(UserDbHelper.UserEntry.TABLE_NAME, null, values,
                            SQLiteDatabase.CONFLICT_REPLACE);
                    mDatabase.close();
                    emitter.onComplete();
                } catch (RuntimeException e) {
                    emitter.onError(e);
                }
            }
        });
    }

    @Override
    public Maybe<List<User>> getAllUser() {
        if (!mDatabase.isOpen()) {
            mDatabase.beginTransaction();
        }
        return Maybe.create(new MaybeOnSubscribe<List<User>>() {
            @Override
            public void subscribe(MaybeEmitter<List<User>> emitter) throws Exception {
                List<User> users = new ArrayList<>();
                Cursor cursor = mDatabase.rawQuery(SELECT_ALL_USER_QUERY, null);
                if (cursor.moveToFirst()) {
                    do {
                        User user = new User();
                        user.setLogin(cursor.getString(cursor.getColumnIndex(
                                UserDbHelper.UserEntry.COLUMN_NAME_USER_LOGIN)));
                        user.setAvatarUrl(cursor.getString(cursor.getColumnIndex(
                                UserDbHelper.UserEntry.COLUMN_NAME_AVATAR_URL)));
                        user.setSubscriptionsUrl(cursor.getString(cursor.getColumnIndex(
                                UserDbHelper.UserEntry.COLUMN_NAME_SUBSCRIPTIONS_URL)));
                        users.add(user);
                    } while (cursor.moveToNext());
                }
                if (!cursor.isClosed()) {
                    cursor.close();
                }

                mDatabase.close();
                emitter.onSuccess(users);
                emitter.onComplete();
            }
        });
    }

    @Override
    public Maybe<User> getUserByUserLogin(final String userLogin) {
        return Maybe.create(new MaybeOnSubscribe<User>() {
            @Override
            public void subscribe(MaybeEmitter<User> emitter) throws Exception {
                if (!mDatabase.isOpen()) {
                    mDatabase.beginTransaction();
                }
                final String[] projection = {
                        UserDbHelper.UserEntry.COLUMN_NAME_USER_LOGIN,
                        UserDbHelper.UserEntry.COLUMN_NAME_AVATAR_URL,
                        UserDbHelper.UserEntry.COLUMN_NAME_SUBSCRIPTIONS_URL
                };
                final String selection = UserDbHelper.UserEntry.COLUMN_NAME_USER_LOGIN + " LIKE ?";
                String[] selectionArgs = { userLogin };

                Cursor cursor =
                        mDatabase.query(UserDbHelper.UserEntry.TABLE_NAME, projection, selection,
                                selectionArgs, null, null, null);
                User user = null;
                if (cursor != null && cursor.getCount() > 0) {
                    user = new User();
                    cursor.moveToFirst();
                    user.setLogin(cursor.getString(
                            cursor.getColumnIndex(UserDbHelper.UserEntry.COLUMN_NAME_USER_LOGIN)));
                    user.setAvatarUrl(cursor.getString(
                            cursor.getColumnIndex(UserDbHelper.UserEntry.COLUMN_NAME_AVATAR_URL)));
                    user.setSubscriptionsUrl(cursor.getString(cursor.getColumnIndex(
                            UserDbHelper.UserEntry.COLUMN_NAME_SUBSCRIPTIONS_URL)));
                }
                if (cursor != null && !cursor.isClosed()) {
                    cursor.close();
                }
                mDatabase.close();
                emitter.onSuccess(user);
                emitter.onComplete();
            }
        });
    }
}
