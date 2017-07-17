package com.fstyle.structure_android.data.source.local.sharedprf

import android.content.Context
import android.content.SharedPreferences

/**
 * Created by le.quang.dao on 14/03/2017.
 */

class SharedPrefsImpl(context: Context) : SharedPrefsApi {

  private val mSharedPreferences: SharedPreferences

  init {
    this.mSharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
  }

  @Suppress("UNCHECKED_CAST")
  override fun <T> get(key: String, clazz: Class<T>): T? {
    if (clazz == String::class.java) {
      return mSharedPreferences.getString(key, "") as T
    } else if (clazz == Boolean::class.java) {
      return java.lang.Boolean.valueOf(mSharedPreferences.getBoolean(key, false)) as T
    } else if (clazz == Float::class.java) {
      return java.lang.Float.valueOf(mSharedPreferences.getFloat(key, 0f)) as T
    } else if (clazz == Int::class.java) {
      return Integer.valueOf(mSharedPreferences.getInt(key, 0)) as T
    } else if (clazz == Long::class.java) {
      return java.lang.Long.valueOf(mSharedPreferences.getLong(key, 0)) as T
    }
    return null
  }

  override fun <T> put(key: String, data: T) {
    val editor = mSharedPreferences.edit()
    if (data is String) {
      editor.putString(key, data)
    } else if (data is Boolean) {
      editor.putBoolean(key, data)
    } else if (data is Float) {
      editor.putFloat(key, data)
    } else if (data is Int) {
      editor.putInt(key, data)
    } else if (data is Long) {
      editor.putLong(key, data)
    }
    editor.apply()
  }

  override fun clear() {
    mSharedPreferences.edit().clear().apply()
  }

  companion object {

    private val PREFS_NAME = "AndroidStructureSharedPreferences"
  }
}
