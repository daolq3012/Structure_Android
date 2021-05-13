package com.ccc.nameapp.utils

import android.text.format.DateFormat
import java.sql.Timestamp
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import java.util.Calendar

object DateUtils {
    const val DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss"
    const val TIME_PATTERN_HH_MM = "HH:mm"
    const val DAY_PATTERN = "dd"
    const val MONTH_PATTERN = "MM"
    const val YEAR_PATTERN = "yyyy"
    const val YYYY_MM_DD_PATTERN = "yyyy-MM-dd"
    const val SERVER_DATE_PATTERN = "yyyy-MM-dd\'T\'HH:mm:ss.sss\'Z\'"

    private const val TODAY_RANGE = 1

    fun convertStringToDate(dateString: String, inputFormat: String): Date? {
        val parser = SimpleDateFormat(inputFormat, Locale.getDefault())
        return try {
            parser.parse(dateString)
        } catch (e: ParseException) {
            null
        }
    }

    private fun convertStringToDateString(
        dateString: String,
        inputFormat: String,
        outputFormat: String
    ): String {
        val gmtTimeZone = TimeZone.getTimeZone("UTC")
        val inputDateTimeFormat = SimpleDateFormat(inputFormat, Locale.getDefault())
        inputDateTimeFormat.timeZone = gmtTimeZone

        val outputDateTimeFormat = SimpleDateFormat(outputFormat, Locale.getDefault())
        outputDateTimeFormat.timeZone = gmtTimeZone

        val dateTime = inputDateTimeFormat.parse(dateString)
        return if (dateTime != null) {
            outputDateTimeFormat.format(dateTime)
        } else ""
    }

    fun convertStringToDate(dateString: String, inputFormat: String, outputFormat: String): Date? {
        return convertStringToDate(
            convertStringToDateString(dateString, inputFormat, outputFormat),
            outputFormat
        )
    }

    fun convertDateToDateString(date: Date, outputFormat: String): String {
        return SimpleDateFormat(outputFormat, Locale.getDefault()).format(date)
    }

    // example: from January 1, 1970, 12:30:15 GMT -> "01-01-1970" -> January 1, 1970, 00:00:00 GMT
    // format: dd-MM-yyyy
    fun changeFormatDate(date: Date, format: String): Date? {
        return convertStringToDate(
            convertDateToDateString(date, outputFormat = format),
            inputFormat = format
        )
    }

    fun getTimeStampWithoutMillis(dateString: String): Long? {
        return try {
            Timestamp.valueOf(dateString).time / 1000
        } catch (e: ParseException) {
            null
        } catch (iae: IllegalArgumentException) {
            null
        }
    }

    fun convertDateStringToTimestamp(dateString: String, format: String): Long? {
        return try {
            convertStringToDate(dateString, format)?.time
        } catch (e: ParseException) {
            null
        } catch (iae: IllegalArgumentException) {
            null
        }
    }

    fun getCurrentTimeStampWithoutMillis(): Long {
        return System.currentTimeMillis() / 1000
    }

    fun getTimeStamp(): Long {
        return System.currentTimeMillis()
    }

    fun convertTimestampToDateString(time: Long, format: String): String {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = time
        return DateFormat.format(format, calendar).toString()
    }

    fun convertTimestampToDate(time: Long): Date {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = time
        return calendar.time
    }

    fun convertTimestampToDate(time: Long, format: String): Date? {
        val date = convertTimestampToDate(time)
        return changeFormatDate(date, format)
    }

    fun getLocalGMT(): String {
        val calendar = Calendar.getInstance(
            TimeZone.getTimeZone("GMT"),
            Locale.getDefault()
        )
        val currentLocalTime = calendar.time
        val date = SimpleDateFormat("Z", Locale.getDefault())
        return date.format(currentLocalTime)
    }

    fun getCurrentDate(): Date {
        return Date()
    }

    fun getCurrentDateString(): String {
        return convertDateToDateString(Calendar.getInstance().time, DATE_TIME_PATTERN)
    }
}
