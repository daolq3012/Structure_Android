package com.fstyle.structure_android.data.source.local.sharedprf

/**
 * Created by le.quang.dao on 14/03/2017.
 */

interface SharedPrefsApi {
  operator fun <T> get(key: String, clazz: Class<T>): T?

  fun <T> put(key: String, data: T)

  fun clear()
}
