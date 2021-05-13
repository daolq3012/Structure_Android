package com.ccc.nameapp.extension

import android.os.Build
import android.text.Html
import android.text.Spanned
import java.text.Normalizer
import java.util.Locale
import java.util.regex.Pattern

fun String.isNotBlank(): Boolean {
    return !this.isBlank()
}

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

fun String.validWithPattern(pattern: Pattern): Boolean {
    return pattern.matcher(toLowerCase(Locale.getDefault())).find()
}

fun String.validWithPattern(regex: String): Boolean {
    return Pattern.compile(regex).matcher(this).find()
}

fun String.formatWithPlural(value: String): String {
    return String.format(this, value)
}

fun String.formatColor(color: String): String {
    return "<font color=$color>$this</font>"
}

fun String.convertToHtml(): Spanned {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
    } else {
        @Suppress("DEPRECATION")
        Html.fromHtml(this)
    }
}

fun String.removeAccent(): String {
    val temp = Normalizer.normalize(this, Normalizer.Form.NFD)
    val pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+")
    return pattern.matcher(temp).replaceAll("").replace('đ', 'd')
        .replace('Đ', 'D')
}

fun String.toBoldStyleHtml(): String {
    return "<strong>$this</strong>"
}
