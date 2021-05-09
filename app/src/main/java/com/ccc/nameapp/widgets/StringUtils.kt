package com.ccc.nameapp.widgets

object StringUtils {
    fun convertListStringsToString(strings: List<String>, format: String): String {
        if (strings.isEmpty()) {
            return ""
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
