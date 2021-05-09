package com.ccc.nameapp.utils

import android.content.Context
import com.ccc.nameapp.R

class Validator(private val context: Context) {

    fun isValidEmail(email: String, isCheckTempMails: Boolean = false): Boolean {
        return if (isCheckTempMails) {
            val tempMails = context.resources.getStringArray(R.array.temp_mails)
            val domainEmail = email.substring(email.lastIndexOf("@") + 1, email.length)
            email.matches(EMAIL_FORMAT.toRegex()) && !tempMails.contains(domainEmail)
        } else {
            email.matches(EMAIL_FORMAT.toRegex())
        }
    }

    companion object {
        const val EMAIL_FORMAT =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
    }
}
