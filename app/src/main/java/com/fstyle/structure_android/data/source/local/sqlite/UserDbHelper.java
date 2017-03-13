package com.fstyle.structure_android.data.source.local.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by le.quang.dao on 13/03/2017.
 */

public class UserDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "StructureAndroid.db";

    private static final String TEXT_TYPE = " TEXT";

    private static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_USER_ENTRIES = "CREATE TABLE "
            + UserEntry.TABLE_NAME
            + " ("
            + UserEntry._ID
            + TEXT_TYPE
            + " PRIMARY KEY,"
            + UserEntry.COLUMN_NAME_USER_LOGIN
            + TEXT_TYPE
            + COMMA_SEP
            + UserEntry.COLUMN_NAME_AVATAR_URL
            + TEXT_TYPE
            + COMMA_SEP
            + UserEntry.COLUMN_NAME_SUBSCRIPTIONS_URL
            + TEXT_TYPE
            + " )";

    public UserDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_USER_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Not required as at version 1
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
        // Not required as at version 1
    }

    /**
     * Inner class that defines the table user contents
     */
    static final class UserEntry implements BaseColumns {
        static final String TABLE_NAME = "user";
        static final String COLUMN_NAME_USER_LOGIN = "user_login";
        static final String COLUMN_NAME_AVATAR_URL = "avatar_url";
        static final String COLUMN_NAME_SUBSCRIPTIONS_URL = "subscriptions_url";
    }
}
