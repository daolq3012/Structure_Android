package com.fstyle.structure_android.data.source.local.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import com.fstyle.structure_android.data.model.User;
import com.fstyle.structure_android.data.source.UserDataSource;
import io.reactivex.Observable;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by le.quang.dao on 10/03/2017.
 */

public class UserLocalDataSource implements UserDataSource.LocalDataSource {

    private static final String SELECT_ALL_USER_QUERY =
            String.format("SELECT * FROM %s", UserDbHelper.UserEntry.TABLE_NAME);

    private UserDbHelper mDbHelper;
    private SQLiteDatabase mDatabase;

    @Inject
    public UserLocalDataSource(@NonNull Context context) {
        mDbHelper = new UserDbHelper(context);
    }

    @Override
    public void openTransaction() {
        mDatabase = mDbHelper.getWritableDatabase();
        mDatabase.beginTransaction();
    }

    @Override
    public void closeTransaction() {
        mDatabase.close();
    }

    public void openReadTransaction() {
        mDbHelper.getReadableDatabase();
    }

    private void readyForWriteDb() {
        if (mDatabase == null || !mDatabase.isOpen()) {
            throw new RuntimeException("Need call openTransaction");
        }
    }

    private void readyForReadDb() {
        if (mDatabase == null || !mDatabase.isOpen()) {
            throw new RuntimeException("Need call openReadTransaction");
        }
    }

    @Override
    public Observable<Object> insertUser(@NonNull final User user) {
        readyForWriteDb();
        return Observable.create(observableEmitter -> {
            ContentValues values = new ContentValues();
            values.put(UserDbHelper.UserEntry.COLUMN_NAME_USER_LOGIN, user.getLogin());
            values.put(UserDbHelper.UserEntry.COLUMN_NAME_AVATAR_URL, user.getAvatarUrl());
            values.put(UserDbHelper.UserEntry.COLUMN_NAME_SUBSCRIPTIONS_URL,
                    user.getSubscriptionsUrl());
            mDatabase.insert(UserDbHelper.UserEntry.TABLE_NAME, null, values);

            observableEmitter.onComplete();
        });
    }

    @Override
    public Observable<Object> updateUser(@NonNull final User user) {
        readyForWriteDb();
        return Observable.create(observableEmitter -> {
            ContentValues values = new ContentValues();
            values.put(UserDbHelper.UserEntry.COLUMN_NAME_USER_LOGIN, user.getLogin());
            values.put(UserDbHelper.UserEntry.COLUMN_NAME_AVATAR_URL, user.getAvatarUrl());
            values.put(UserDbHelper.UserEntry.COLUMN_NAME_SUBSCRIPTIONS_URL,
                    user.getSubscriptionsUrl());
            mDatabase.update(UserDbHelper.UserEntry.TABLE_NAME, values,
                    UserDbHelper.UserEntry.COLUMN_NAME_USER_LOGIN + "= ?",
                    new String[] { user.getLogin() });
            observableEmitter.onComplete();
        });
    }

    @Override
    public Observable<Object> deleteUser(@NonNull final User user) {
        readyForWriteDb();
        return Observable.create(observableEmitter -> {
            final String selection = UserDbHelper.UserEntry.COLUMN_NAME_USER_LOGIN + " LIKE ?";
            final String[] selectionArgs = { user.getLogin() };

            mDatabase.delete(UserDbHelper.UserEntry.TABLE_NAME, selection, selectionArgs);

            observableEmitter.onComplete();
        });
    }

    @Override
    public Observable<Object> insertOrUpdateUser(@NonNull final User user) {
        readyForWriteDb();
        return Observable.create(observableEmitter -> {
            ContentValues values = new ContentValues();
            values.put(UserDbHelper.UserEntry.COLUMN_NAME_USER_LOGIN, user.getLogin());
            values.put(UserDbHelper.UserEntry.COLUMN_NAME_AVATAR_URL, user.getAvatarUrl());
            values.put(UserDbHelper.UserEntry.COLUMN_NAME_SUBSCRIPTIONS_URL,
                    user.getSubscriptionsUrl());
            mDatabase.insertWithOnConflict(UserDbHelper.UserEntry.TABLE_NAME, null, values,
                    SQLiteDatabase.CONFLICT_REPLACE);

            observableEmitter.onComplete();
        });
    }

    @Override
    public Observable<List<User>> getAllUser() {
        readyForReadDb();
        return Observable.create(observableEmitter -> {
            List<User> users = new ArrayList<>();
            Cursor cursor = mDatabase.rawQuery(SELECT_ALL_USER_QUERY, null);
            if (cursor.moveToFirst()) {
                do {
                    User user = new User();
                    user.setLogin(cursor.getString(
                            cursor.getColumnIndex(UserDbHelper.UserEntry.COLUMN_NAME_USER_LOGIN)));
                    user.setAvatarUrl(cursor.getString(
                            cursor.getColumnIndex(UserDbHelper.UserEntry.COLUMN_NAME_AVATAR_URL)));
                    user.setSubscriptionsUrl(cursor.getString(cursor.getColumnIndex(
                            UserDbHelper.UserEntry.COLUMN_NAME_SUBSCRIPTIONS_URL)));
                    users.add(user);
                } while (cursor.moveToNext());
            }
            if (!cursor.isClosed()) {
                cursor.close();
            }

            observableEmitter.onNext(users);
            observableEmitter.onComplete();
        });
    }

    @Override
    public Observable<User> getUserByUserLogin(final String userLogin) {
        readyForReadDb();
        return Observable.create(observableEmitter -> {
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

            observableEmitter.onNext(user);
            observableEmitter.onComplete();
        });
    }
}
