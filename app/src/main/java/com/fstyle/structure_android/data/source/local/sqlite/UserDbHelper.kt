package com.fstyle.structure_android.data.source.local.sqlite

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

/**
 * Created by le.quang.dao on 13/03/2017.
 */

class UserDbHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null,
    DATABASE_VERSION) {

  override fun onCreate(db: SQLiteDatabase) {
    db.execSQL(SQL_CREATE_USER_ENTRIES)
  }

  override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
    // Not required as at version 1
  }

  override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
    super.onDowngrade(db, oldVersion, newVersion)
    // Not required as at version 1
  }

  /**
   * Inner class that defines the table user contents
   */
  internal class UserEntry : BaseColumns {
    companion object {
      val TABLE_NAME = "user"
      val COLUMN_NAME_USER_LOGIN = "user_login"
      val COLUMN_NAME_AVATAR_URL = "avatar_url"
      val COLUMN_NAME_SUBSCRIPTIONS_URL = "subscriptions_url"
    }
  }

  companion object {

    private val DATABASE_VERSION = 1

    private val DATABASE_NAME = "StructureAndroid.db"

    private val TEXT_TYPE = " TEXT"

    private val COMMA_SEP = ","

    private val SQL_CREATE_USER_ENTRIES = "CREATE TABLE " + UserEntry.TABLE_NAME + " (" + BaseColumns._ID + TEXT_TYPE + " PRIMARY KEY," + UserEntry.COLUMN_NAME_USER_LOGIN + TEXT_TYPE + COMMA_SEP + UserEntry.COLUMN_NAME_AVATAR_URL + TEXT_TYPE + COMMA_SEP + UserEntry.COLUMN_NAME_SUBSCRIPTIONS_URL + TEXT_TYPE + " )"
  }
}
