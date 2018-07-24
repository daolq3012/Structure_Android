package com.ccc.nameproject.extension

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by daolq on 5/16/18.
 * nameproject_Android
 */

fun Date.toString(format: String): String {
  return SimpleDateFormat(format, Locale.US).format(this)
}
