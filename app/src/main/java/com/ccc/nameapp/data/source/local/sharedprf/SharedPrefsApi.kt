package com.ccc.nameapp.data.source.local.sharedprf

import android.content.Context
import android.content.SharedPreferences
import com.ccc.nameapp.di.scopes.AppScoped
import com.google.gson.Gson
import javax.inject.Inject

interface SharedPrefsApi {
    fun <T> get(key: String, clazz: Class<T>): T
    fun <T> put(key: String, data: T)
    fun clear()
    fun clearKey(key: String)
}

@AppScoped
class SharedPrefsImpl @Inject constructor(context: Context) : SharedPrefsApi {

    private var mSharedPreferences: SharedPreferences = context.getSharedPreferences(
        SharedPrefsKey.PREF_NAME,
        Context.MODE_PRIVATE
    )

    override fun <T> get(key: String, clazz: Class<T>): T {
        return when (clazz) {
            String::class.java -> mSharedPreferences.getString(key, "").let { it as T }
            Boolean::class.java -> mSharedPreferences.getBoolean(key, false).let { it as T }
            Float::class.java -> mSharedPreferences.getFloat(key, 0f).let { it as T }
            Int::class.java -> mSharedPreferences.getInt(key, 0).let { it as T }
            Long::class.java -> mSharedPreferences.getLong(key, 0).let { it as T }
            else -> Gson().fromJson(mSharedPreferences.getString(key, ""), clazz)
        }
    }

    override fun <T> put(key: String, data: T) {
        val editor = mSharedPreferences.edit()
        when (data) {
            is String -> editor.putString(key, data)
            is Boolean -> editor.putBoolean(key, data)
            is Float -> editor.putFloat(key, data)
            is Int -> editor.putInt(key, data)
            is Long -> editor.putLong(key, data)
            else -> editor.putString(key, Gson().toJson(data))
        }
        editor.apply()
    }

    override fun clear() {
        mSharedPreferences.edit()?.clear()?.apply()
    }

    override fun clearKey(key: String) {
        val editor = mSharedPreferences.edit()
        editor.remove(key)
        editor.apply()
    }
}
