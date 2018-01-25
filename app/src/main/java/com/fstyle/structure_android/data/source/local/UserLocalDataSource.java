package com.fstyle.structure_android.data.source.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import com.fstyle.structure_android.data.model.User;
import com.fstyle.structure_android.data.source.UserDataSource;
import com.fstyle.structure_android.data.source.local.config.sqlite.UserDbHelper;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by daolq on 1/10/18.
 */

public class UserLocalDataSource implements UserDataSource.LocalDataSource {

    private static final String SELECT_ALL_USER_QUERY =
            String.format("SELECT * FROM %s", UserDbHelper.UserEntry.TABLE_NAME);

    private static UserLocalDataSource instance;

    private UserDbHelper mHelper;

    public UserLocalDataSource(UserDbHelper userDbHelper) {
        mHelper = userDbHelper;
    }

    public static synchronized UserLocalDataSource getInstance(@NonNull Context context) {
        if (instance == null) {
            instance = new UserLocalDataSource(UserDbHelper.getInstance(context));
        }
        return instance;
    }

    /**
     * Used to force {@link #getInstance(Context)} to create a new instance
     * next time it's called.
     */
    public static void destroyInstance() {
        instance = null;
    }

    @Override
    public Completable insertUser(@NonNull final User user) {
        return Completable.create(emitter -> {
            try {
                SQLiteDatabase database = mHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(UserDbHelper.UserEntry.COLUMN_NAME_USER_LOGIN, user.getLogin());
                values.put(UserDbHelper.UserEntry.COLUMN_NAME_AVATAR_URL, user.getAvatarUrl());
                values.put(UserDbHelper.UserEntry.COLUMN_NAME_SUBSCRIPTIONS_URL,
                        user.getSubscriptionsUrl());
                database.insert(UserDbHelper.UserEntry.TABLE_NAME, null, values);
                database.close();
                emitter.onComplete();
            } catch (Exception e) {
                emitter.onError(e);
            }
        });
    }

    @Override
    public Completable updateUser(@NonNull final User user) {
        return Completable.create(emitter -> {
            try {
                SQLiteDatabase database = mHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(UserDbHelper.UserEntry.COLUMN_NAME_USER_LOGIN, user.getLogin());
                values.put(UserDbHelper.UserEntry.COLUMN_NAME_AVATAR_URL, user.getAvatarUrl());
                values.put(UserDbHelper.UserEntry.COLUMN_NAME_SUBSCRIPTIONS_URL,
                        user.getSubscriptionsUrl());
                database.update(UserDbHelper.UserEntry.TABLE_NAME, values,
                        UserDbHelper.UserEntry.COLUMN_NAME_AVATAR_URL + "= ?",
                        new String[] { user.getAvatarUrl() });
                database.close();
                emitter.onComplete();
            } catch (Exception e) {
                emitter.onError(e);
            }
        });
    }

    @Override
    public Completable deleteUser(@NonNull final User user) {
        return Completable.create(emitter -> {
            try {
                SQLiteDatabase database = mHelper.getWritableDatabase();
                final String selection = UserDbHelper.UserEntry.COLUMN_NAME_USER_LOGIN + " LIKE ?";
                final String[] selectionArgs = { user.getLogin() };
                database.delete(UserDbHelper.UserEntry.TABLE_NAME, selection, selectionArgs);
                database.close();
                emitter.onComplete();
            } catch (Exception e) {
                emitter.onError(e);
            }
        });
    }

    @Override
    public Completable insertOrUpdateUser(@NonNull final User user) {
        return Completable.create(emitter -> {
            try {
                SQLiteDatabase database = mHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(UserDbHelper.UserEntry.COLUMN_NAME_USER_LOGIN, user.getLogin());
                values.put(UserDbHelper.UserEntry.COLUMN_NAME_AVATAR_URL, user.getAvatarUrl());
                values.put(UserDbHelper.UserEntry.COLUMN_NAME_SUBSCRIPTIONS_URL,
                        user.getSubscriptionsUrl());
                database.insertWithOnConflict(UserDbHelper.UserEntry.TABLE_NAME, null, values,
                        SQLiteDatabase.CONFLICT_REPLACE);
                database.close();
                emitter.onComplete();
            } catch (Exception e) {
                emitter.onError(e);
            }
        });
    }

    @Override
    public Flowable<List<User>> getUsers() {
        return Flowable.create(emitter -> {
            SQLiteDatabase database = mHelper.getReadableDatabase();
            List<User> users = new ArrayList<>();
            Cursor cursor = database.rawQuery(SELECT_ALL_USER_QUERY, null);
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

            database.close();
            emitter.onNext(users);
            emitter.onComplete();
        }, BackpressureStrategy.BUFFER);
    }

    @Override
    public Flowable<User> getUser(final String avatarUrl) {
        return Flowable.create(emitter -> {
            SQLiteDatabase database = mHelper.getReadableDatabase();
            final String[] projection = {
                    UserDbHelper.UserEntry.COLUMN_NAME_USER_LOGIN,
                    UserDbHelper.UserEntry.COLUMN_NAME_AVATAR_URL,
                    UserDbHelper.UserEntry.COLUMN_NAME_SUBSCRIPTIONS_URL
            };
            final String selection = UserDbHelper.UserEntry.COLUMN_NAME_AVATAR_URL + " LIKE ?";
            String[] selectionArgs = { avatarUrl };

            Cursor cursor = database.query(UserDbHelper.UserEntry.TABLE_NAME, projection, selection,
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
            database.close();
            emitter.onNext(user);
            emitter.onComplete();
        }, BackpressureStrategy.BUFFER);
    }

    @Override
    public Flowable<List<User>> searchUsers(String userName) {
        return Flowable.create(emitter -> {
            SQLiteDatabase database = mHelper.getReadableDatabase();
            final String[] columns = {
                    UserDbHelper.UserEntry.COLUMN_NAME_USER_LOGIN,
                    UserDbHelper.UserEntry.COLUMN_NAME_AVATAR_URL,
                    UserDbHelper.UserEntry.COLUMN_NAME_SUBSCRIPTIONS_URL
            };
            final String selection = UserDbHelper.UserEntry.COLUMN_NAME_USER_LOGIN + " LIKE ?";
            String[] selectionArgs = { "%" + userName + "%" };

            Cursor cursor = database.query(UserDbHelper.UserEntry.TABLE_NAME, columns, selection,
                    selectionArgs, null, null, null);
            List<User> users = new ArrayList<>();
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
            database.close();
            emitter.onNext(users);
            emitter.onComplete();
        }, BackpressureStrategy.BUFFER);
    }

    @Override
    public Completable deleteAllUsers() {
        return Completable.create(emitter -> {
            try {
                SQLiteDatabase database = mHelper.getWritableDatabase();
                database.delete(UserDbHelper.UserEntry.TABLE_NAME, null, null);
                database.close();
                emitter.onComplete();
            } catch (RuntimeException e) {
                emitter.onError(e);
            }
        });
    }
}
