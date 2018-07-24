package com.ccc.nameproject.extension

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

/**
 * Created by daolq on 5/15/18.
 * nameproject_Android
 */

fun String.toInt(): Int {
  return try {
    Integer.parseInt(this)
  } catch (e: NumberFormatException) {
    Integer.MIN_VALUE
  }
}

fun String.toDouble(): Double {
  return try {
    java.lang.Double.parseDouble(this)
  } catch (e: NumberFormatException) {
    Double.MIN_VALUE
  }
}

@Throws(ParseException::class)
fun String.toDate(format: String): Date {
  val parser = SimpleDateFormat(format, Locale.getDefault())
  return parser.parse(this)
}

@Throws(ParseException::class)
fun String.toDateWithFormat(inputFormat: String, outputFormat: String): String {
  val gmtTimeZone = TimeZone.getTimeZone("UTC")
  val inputDateTimeFormat = SimpleDateFormat(inputFormat, Locale.getDefault())
  inputDateTimeFormat.timeZone = gmtTimeZone

  val outputDateTimeFormat = SimpleDateFormat(outputFormat, Locale.getDefault())
  outputDateTimeFormat.timeZone = gmtTimeZone
  return outputDateTimeFormat.format(inputDateTimeFormat.parse(this))
}

fun String.validWithPattern(pattern: Pattern): Boolean {
  return pattern.matcher(toLowerCase()).find()
}

fun String.validWithPattern(regex: String): Boolean {
  return Pattern.compile(regex).matcher(this).find()
}

