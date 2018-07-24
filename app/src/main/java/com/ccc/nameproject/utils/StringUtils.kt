package com.ccc.nameproject.utils

/**
 * Created by daolq on 5/5/18.
 */

object StringUtils {
   fun convertStringToListStringWithFormatPattern(strings: List<String>,
      format: String): String? {
    if (strings.isEmpty()) {
      return null
    }
    val builder = StringBuilder()
    for (s in strings) {
      builder.append(s)
      builder.append(format)
    }
    var result = builder.toString()
    result = result.substring(0, result.length - format.length)
    return result
  }
}