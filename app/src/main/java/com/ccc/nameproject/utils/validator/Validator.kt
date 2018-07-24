package com.ccc.nameproject.utils.validator

import android.content.Context
import android.util.Log
import com.ccc.nameproject.R
import com.ccc.nameproject.extension.notNull
import com.ccc.nameproject.extension.validWithPattern
import com.ccc.nameproject.utils.Constant
import com.ccc.nameproject.utils.StringUtils
import com.ccc.nameproject.utils.rx.BaseSchedulerProvider
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*
import java.util.regex.Pattern

/**
 * Created by daolq on 5/17/18.
 * nameproject_Android
 */
class Validator(private val context: Context) {

  private var mNotGoodPattern: Pattern? = null

  fun validateName(context: Context, name: String): String? {
    val errorMessages = ArrayList<String>()
    if (name.isBlank()) {
      return context.resources.getString(R.string.empty_field)
    }
    if (name.length > MAX_LENGTH_NAME_FORMAT) {
      errorMessages.add(context.resources.getString(R.string.more_than_255_char))
    }
    if (name.validWithPattern(ALL_SPECIAL_CHAR_FORMAT)) {
      errorMessages.add(context.resources.getString(R.string.contain_special_char))
    }

    mNotGoodPattern.notNull {
      val isValid = name.validWithPattern(it)
      val message = if (isValid) "" else context.getString(R.string.contain_invalid_word)
      if (message.isNotBlank()) errorMessages.add(message)
    }

    return StringUtils.convertStringToListStringWithFormatPattern(errorMessages,
        Constant.ENTER_SPACE_FORMAT)
  }

  fun validateEmail(email: String): String? {
    if (email.isBlank()) {
      return context.resources.getString(R.string.empty_field)
    }
    return if (!email.matches(EMAIL_FORMAT.toRegex())) {
      context.resources.getString(R.string.wrong_email_format)
    } else null
  }

  fun validatePassword(password: String): String? {
    val list = ArrayList<String>()
    if (password.isBlank()) {
      return context.resources.getString(R.string.empty_field)
    }
    if (password.length < LENGTH_PASSWORD_FORMAT) {
      list.add(context.resources.getString(R.string.less_than_8_char))
    }
    if (!password.validWithPattern(LOWER_CASE_FORMAT) || !password.validWithPattern(
            UPPER_CASE_FORMAT)) {
      list.add(context.resources.getString(R.string.upper_and_lower_case))
    }
    if (!password.validWithPattern(NUMBER_FORMAT) || !password.validWithPattern(
            SPECIAL_CHAR_FORMAT)) {
      list.add(context.resources.getString(R.string.at_least_1_number_or_special_char))
    }
    return StringUtils.convertStringToListStringWithFormatPattern(list,
        Constant.ENTER_SPACE_FORMAT)
  }

  fun initNGWordPattern(baseScheduler: BaseSchedulerProvider): Disposable {
    return Observable.create<Pattern> { emitter ->
      run {
        val reader = BufferedReader(InputStreamReader(context.assets.open("ng-word")))
        val buffer = StringBuilder()
        val line: String? = reader.readLine()
        while (line != null) {
          if (buffer.isEmpty()) {
            buffer.append("(").append(line.toLowerCase(Locale.ENGLISH))
          } else {
            buffer.append("|").append(line.toLowerCase(Locale.ENGLISH))
          }
        }
        emitter.onNext(Pattern.compile(buffer.append(")").toString()))
      }
    }.subscribeOn(baseScheduler.io())
        .observeOn(baseScheduler.ui())
        .subscribe({ pattern -> mNotGoodPattern = pattern },
            { error -> Log.e("Validator", error.message) })
  }


  companion object {
    private const val NUMBER_FORMAT = "[0-9]"
    private const val LOWER_CASE_FORMAT = "[a-z]"
    private const val UPPER_CASE_FORMAT = "[A-Z]"
    private const val SPECIAL_CHAR_FORMAT = "[!@#%^]"
    private const val ALL_SPECIAL_CHAR_FORMAT = "[!@#$%^&*]"
    private const val EMAIL_FORMAT = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    private const val LENGTH_PASSWORD_FORMAT = 8
    private const val MAX_LENGTH_NAME_FORMAT = 255
  }
}