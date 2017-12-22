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

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;


/**
 * Created by le.quang.dao on 10/03/2017.
 */

public class UserRepository implements UserDataSource.RemoteDataSource, UserDataSource.LocalDataSource {

    private static final String SELECT_ALL_USER_QUERY =
            String.format("SELECT * FROM %s", UserDbHelper.UserEntry.TABLE_NAME);

    private NameApi mNameApi;
    private SQLiteDatabase mDatabase;

    public UserRepository(SQLiteDatabase database, NameApi api) {
        mDatabase = database;
        mNameApi = api;
    }

    @Override
    public Observable<List<User>> searchUsers(String keyWord, String limit) {
        return mNameApi.searchGithubUsers(keyWord, limit)
                .flatMap(new Function<SearchUserResponse, ObservableSource<List<User>>>() {
                    @Override
                    public ObservableSource<List<User>> apply(SearchUserResponse searchUserResponse) throws Exception {
                        return Observable.just(searchUserResponse.getItems());
                    }
                });
    }

    @Override
    public Observable<Void> insertUser(@NonNull final User user) {
        mDatabase.beginTransaction();
        return Observable.create(new ObservableOnSubscribe<Void>() {
            @Override
            public void subscribe(ObservableEmitter<Void> e) throws Exception {
                ContentValues values = new ContentValues();
                values.put(UserDbHelper.UserEntry.COLUMN_NAME_USER_LOGIN, user.getLogin());
                values.put(UserDbHelper.UserEntry.COLUMN_NAME_AVATAR_URL, user.getAvatarUrl());
                values.put(UserDbHelper.UserEntry.COLUMN_NAME_SUBSCRIPTIONS_URL,
                        user.getSubscriptionsUrl());
                mDatabase.insert(UserDbHelper.UserEntry.TABLE_NAME, null, values);

                e.onComplete();
                mDatabase.close();
            }
        });
    }

    @Override
    public Observable<Void> updateUser(@NonNull final User user) {
        return Observable.create(new ObservableOnSubscribe<Void>() {
            @Override
            public void subscribe(ObservableEmitter<Void> e) throws Exception {
                if (!mDatabase.isOpen()) {
                    mDatabase.beginTransaction();
                }                ContentValues values = new ContentValues();
                values.put(UserDbHelper.UserEntry.COLUMN_NAME_USER_LOGIN, user.getLogin());
                values.put(UserDbHelper.UserEntry.COLUMN_NAME_AVATAR_URL, user.getAvatarUrl());
                values.put(UserDbHelper.UserEntry.COLUMN_NAME_SUBSCRIPTIONS_URL,
                        user.getSubscriptionsUrl());
                mDatabase.update(UserDbHelper.UserEntry.TABLE_NAME, values,
                        UserDbHelper.UserEntry.COLUMN_NAME_USER_LOGIN + "= ?",
                        new String[]{user.getLogin()});

                e.onComplete();
                mDatabase.close();
            }
        });
    }

    @Override
    public Observable<Void> deleteUser(@NonNull final User user) {
        return Observable.create(new ObservableOnSubscribe<Void>() {
            @Override
            public void subscribe(ObservableEmitter<Void> e) throws Exception {
                if (!mDatabase.isOpen()) {
                    mDatabase.beginTransaction();
                }
                final String selection = UserDbHelper.UserEntry.COLUMN_NAME_USER_LOGIN + " LIKE ?";
                final String[] selectionArgs = {user.getLogin()};

                mDatabase.delete(UserDbHelper.UserEntry.TABLE_NAME, selection, selectionArgs);

                e.onComplete();
                mDatabase.close();
            }
        });
    }

    @Override
    public Observable<Void> insertOrUpdateUser(@NonNull final User user) {
        return Observable.create(new ObservableOnSubscribe<Void>() {
            @Override
            public void subscribe(ObservableEmitter<Void> e) throws Exception {
                if (!mDatabase.isOpen()) {
                    mDatabase.beginTransaction();
                }
                ContentValues values = new ContentValues();
                values.put(UserDbHelper.UserEntry.COLUMN_NAME_USER_LOGIN, user.getLogin());
                values.put(UserDbHelper.UserEntry.COLUMN_NAME_AVATAR_URL, user.getAvatarUrl());
                values.put(UserDbHelper.UserEntry.COLUMN_NAME_SUBSCRIPTIONS_URL,
                        user.getSubscriptionsUrl());
                mDatabase.insertWithOnConflict(UserDbHelper.UserEntry.TABLE_NAME, null, values,
                        SQLiteDatabase.CONFLICT_REPLACE);

                e.onComplete();
                mDatabase.close();
            }
        });
    }

    @Override
    public Observable<List<User>> getAllUser() {
        return Observable.create(new ObservableOnSubscribe<List<User>>() {
            @Override
            public void subscribe(ObservableEmitter<List<User>> e) throws Exception {
                if (!mDatabase.isOpen()) {
                    mDatabase.beginTransaction();
                }
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

                e.onNext(users);
                e.onComplete();
                mDatabase.close();
            }
        });
    }

    @Override
    public Observable<User> getUserByUserLogin(final String userLogin) {
        return Observable.create(new ObservableOnSubscribe<User>() {
            @Override
            public void subscribe(ObservableEmitter<User> e) throws Exception {
                if (!mDatabase.isOpen()) {
                    mDatabase.beginTransaction();
                }
                final String[] projection = {
                        UserDbHelper.UserEntry.COLUMN_NAME_USER_LOGIN,
                        UserDbHelper.UserEntry.COLUMN_NAME_AVATAR_URL,
                        UserDbHelper.UserEntry.COLUMN_NAME_SUBSCRIPTIONS_URL
                };
                final String selection = UserDbHelper.UserEntry.COLUMN_NAME_USER_LOGIN + " LIKE ?";
                String[] selectionArgs = {userLogin};

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

                e.onNext(user);
                e.onComplete();
                mDatabase.close();
            }
        });
    }
}
