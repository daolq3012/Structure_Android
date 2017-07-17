package com.fstyle.structure_android.utils.common

/**
 * Created by framgia on 05/05/2017.
 */

object StringUtils {

  fun isBlank(input: String): Boolean {
    return input.isEmpty()
  }

  fun isNotBlank(input: String): Boolean {
    return !isBlank(input)
  }

  fun convertStringToNumber(input: String?): Int {
    try {
      return Integer.parseInt(input)
    } catch (e: NumberFormatException) {
      return Integer.MIN_VALUE
    }
  }
}
