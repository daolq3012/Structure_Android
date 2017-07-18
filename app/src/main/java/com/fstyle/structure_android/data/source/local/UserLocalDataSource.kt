package com.fstyle.structure_android.data.source.local

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.fstyle.structure_android.data.model.User
import com.fstyle.structure_android.data.source.UserDataSource
import com.fstyle.structure_android.data.source.local.sqlite.UserDbHelper
import io.reactivex.BackpressureStrategy
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import java.util.*
import javax.inject.Inject

/**
 * Created by le.quang.dao on 10/03/2017.
 */

class UserLocalDataSource @Inject
constructor(context: Context) : UserDataSource.LocalDataSource {

  private val mDbHelper: UserDbHelper = UserDbHelper(
      context)
  private var mDatabase: SQLiteDatabase? = null

  override fun openTransaction() {
    mDatabase = mDbHelper.writableDatabase
    mDatabase!!.beginTransaction()
  }

  override fun closeTransaction() {
    mDatabase!!.close()
  }

  fun openReadTransaction() {
    mDbHelper.readableDatabase
  }

  private fun readyForWriteDb() {
    if (mDatabase == null || !mDatabase!!.isOpen) {
      throw RuntimeException("Need call openTransaction")
    }
  }

  private fun readyForReadDb() {
    if (mDatabase == null || !mDatabase!!.isOpen) {
      throw RuntimeException("Need call openReadTransaction")
    }
  }

  override fun insertUser(user: User): Completable {
    readyForWriteDb()
    return Completable.create { completableEmitter ->
      val values = ContentValues()
      values.put(
          UserDbHelper.UserEntry.COLUMN_NAME_USER_LOGIN, user.login)
      values.put(
          UserDbHelper.UserEntry.COLUMN_NAME_AVATAR_URL, user.avatarUrl)
      values.put(
          UserDbHelper.UserEntry.COLUMN_NAME_SUBSCRIPTIONS_URL,
          user.subscriptionsUrl)
      mDatabase!!.insert(
          UserDbHelper.UserEntry.TABLE_NAME, null, values)

      completableEmitter.onComplete()
    }
  }

  override fun updateUser(user: User): Completable {
    readyForWriteDb()
    return Completable.create { completableEmitter ->
      val values = ContentValues()
      values.put(
          UserDbHelper.UserEntry.COLUMN_NAME_USER_LOGIN, user.login)
      values.put(
          UserDbHelper.UserEntry.COLUMN_NAME_AVATAR_URL, user.avatarUrl)
      values.put(
          UserDbHelper.UserEntry.COLUMN_NAME_SUBSCRIPTIONS_URL,
          user.subscriptionsUrl)
      mDatabase!!.update(
          UserDbHelper.UserEntry.TABLE_NAME, values,
          UserDbHelper.UserEntry.COLUMN_NAME_USER_LOGIN + "= ?",
          arrayOf(user.login))
      completableEmitter.onComplete()
    }
  }

  override fun deleteUser(user: User): Completable {
    readyForWriteDb()
    return Completable.create { completableEmitter ->
      val selection = UserDbHelper.UserEntry.COLUMN_NAME_USER_LOGIN + " LIKE ?"
      val selectionArgs = arrayOf(user.login)

      mDatabase!!.delete(
          UserDbHelper.UserEntry.TABLE_NAME, selection, selectionArgs)

      completableEmitter.onComplete()
    }
  }

  override fun insertOrUpdateUser(user: User): Completable {
    readyForWriteDb()
    return Completable.create { completableEmitter ->
      val values = ContentValues()
      values.put(
          UserDbHelper.UserEntry.COLUMN_NAME_USER_LOGIN, user.login)
      values.put(
          UserDbHelper.UserEntry.COLUMN_NAME_AVATAR_URL, user.avatarUrl)
      values.put(
          UserDbHelper.UserEntry.COLUMN_NAME_SUBSCRIPTIONS_URL,
          user.subscriptionsUrl)
      mDatabase!!.insertWithOnConflict(
          UserDbHelper.UserEntry.TABLE_NAME, null, values,
          SQLiteDatabase.CONFLICT_REPLACE)

      completableEmitter.onComplete()
    }
  }

  override val allUser: Flowable<List<User>>
    get() {
      readyForReadDb()
      return Flowable.create({ flowableEmitter ->
        val users = ArrayList<User>()
        val cursor = mDatabase!!.rawQuery(
            SELECT_ALL_USER_QUERY, null)
        if (cursor.moveToFirst()) {
          do {
            val user = User()
            user.login = cursor.getString(cursor.getColumnIndex(
                UserDbHelper.UserEntry.COLUMN_NAME_USER_LOGIN))
            user.avatarUrl = cursor.getString(cursor.getColumnIndex(
                UserDbHelper.UserEntry.COLUMN_NAME_AVATAR_URL))
            user.subscriptionsUrl = cursor.getString(cursor.getColumnIndex(
                UserDbHelper.UserEntry.COLUMN_NAME_SUBSCRIPTIONS_URL))
            users.add(user)
          } while (cursor.moveToNext())
        }
        if (!cursor.isClosed) {
          cursor.close()
        }

        flowableEmitter.onNext(users)
        flowableEmitter.onComplete()
      }, BackpressureStrategy.LATEST)
    }

  override fun getUserByUserLogin(userLogin: String): Single<User> {
    readyForReadDb()
    return Single.create { singleEmitter ->
      val projection = arrayOf(
          UserDbHelper.UserEntry.COLUMN_NAME_USER_LOGIN,
          UserDbHelper.UserEntry.COLUMN_NAME_AVATAR_URL,
          UserDbHelper.UserEntry.COLUMN_NAME_SUBSCRIPTIONS_URL)
      val selection = UserDbHelper.UserEntry.COLUMN_NAME_USER_LOGIN + " LIKE ?"
      val selectionArgs = arrayOf(userLogin)

      val cursor = mDatabase!!.query(
          UserDbHelper.UserEntry.TABLE_NAME, projection, selection,
          selectionArgs, null, null, null)
      var user: User? = null
      if (cursor != null && cursor.count > 0) {
        user = User()
        cursor.moveToFirst()
        user.login = cursor.getString(
            cursor.getColumnIndex(
                UserDbHelper.UserEntry.COLUMN_NAME_USER_LOGIN))
        user.avatarUrl = cursor.getString(
            cursor.getColumnIndex(
                UserDbHelper.UserEntry.COLUMN_NAME_AVATAR_URL))
        user.subscriptionsUrl = cursor.getString(cursor.getColumnIndex(
            UserDbHelper.UserEntry.COLUMN_NAME_SUBSCRIPTIONS_URL))
      }
      if (cursor != null && !cursor.isClosed) {
        cursor.close()
      }
      if (user != null) {
        singleEmitter.onSuccess(user)
      } else {
        singleEmitter.onError(Throwable("Not Found"))
      }
    }
  }

  companion object {

    private val SELECT_ALL_USER_QUERY = String.format("SELECT * FROM %s",
        UserDbHelper.UserEntry.TABLE_NAME)
  }
}
