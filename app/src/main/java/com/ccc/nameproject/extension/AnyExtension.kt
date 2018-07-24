package com.ccc.nameproject.extension

/**
 * Created by daolq on 5/15/18.
 * nameproject_Android
 */

inline fun <T : Any> T?.notNull(f: (it: T) -> Unit) {
  if (this != null) f(this)
}

inline fun <T : Any> T?.isNull(f: () -> Unit) {
  if (this == null) f()
}
