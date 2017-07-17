package com.fstyle.structure_android.utils.validator

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.text.TextUtils
import android.util.Log
import android.util.SparseArray
import android.util.SparseIntArray
import com.fstyle.structure_android.screen.BaseViewModel
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method
import java.util.*
import java.util.regex.Pattern

@Suppress("IMPLICIT_BOXING_IN_IDENTITY_EQUALS")
/**
 * Created by le.quang.dao on 16/03/2017.
 */

class Validator(@param:ApplicationContext private val mContext: Context, clzz: Class<*>) {
  private val mValidatedMethods: SparseArray<Method>
  private var mMessage: String? = null

  private val mAllErrorMessage: SparseIntArray

  private var mNGWordPattern: Pattern? = null

  init {
    if (mContext is Activity) {
      throw ValidationException(
          "Context should be get From Application to avoid leak memory")
    }
    mValidatedMethods = cacheValidatedMethod()
    mAllErrorMessage = getAllErrorMessage(clzz)
  }

  private fun cacheValidatedMethod(): SparseArray<Method> {
    val methods = SparseArray<Method>()
    for (method in this.javaClass.declaredMethods) {
      if (method.isAnnotationPresent(ValidMethod::class.java)) {
        val validMethod = method.getAnnotation(ValidMethod::class.java)
        for (type in validMethod.type) {
          methods.put(type, method)
        }
      }
    }
    return methods
  }

  private fun getAllErrorMessage(clzz: Class<*>): SparseIntArray {
    @SuppressLint("UseSparseArrays") val errorMessages = SparseIntArray()
    for (field in clzz.declaredFields) {
      val annotation = field.getAnnotation(Validation::class.java) ?: continue
      val rules = annotation.value
      field.isAccessible = true
      for (rule in rules) {
        for (type in rule.types) {
          errorMessages.put(type, rule.message)
        }
      }
    }
    return errorMessages
  }

  /**
   * Check if an object in Validator Model with annotation [Optional] is valid
   */
  private fun isValidOptional(factor: Any?): Boolean {
    if (factor is Int) {
      return factor as Int? === 0
    }
    if (factor is Long) {
      return factor as Long? === 0L
    }
    if (factor is Float) {
      return factor as Float? === 0.0f
    }
    if (factor is Double) {
      return factor as Double? === 0.0
    }
    if (factor is String) {
      return TextUtils.isEmpty(factor as String?)
    }
    return factor is Boolean && !(factor as Boolean?)!! || factor == null
  }

  /**
   * Validate all field.
   */
  private fun validate(factor: Any, rules: Array<out Rule>, isOptional: Boolean): Boolean {
    var isValid = true
    for (rule in rules) {
      rule.types
          .asSequence()
          .mapNotNull { mValidatedMethods.get(it) }
          .forEach {
            try {
              val valid = isOptional && isValidOptional(factor) || TextUtils.isEmpty(
                  it.invoke(this, factor) as String)
              if (!valid) {
                isValid = false
              }
            } catch (e: IllegalAccessException) {
              isValid = false
              Log.e(TAG, "validate: ", e)
            } catch (e: InvocationTargetException) {
              isValid = false
              Log.e(TAG, "validate: ", e)
            }
          }
    }
    return isValid
  }

  @Throws(IllegalAccessException::class)
  fun <T : BaseViewModel> validateAll(`object`: T): Boolean {
    var isValid = true

    for (field in `object`.javaClass.declaredFields) {
      val annotation = field.getAnnotation(Validation::class.java) ?: continue
      val rules = annotation.value
      val optional = field.getAnnotation(Optional::class.java)
      val isOptional = optional != null
      field.isAccessible = true

      val obj = field.get(`object`)
      val valid = validate(obj, rules, isOptional)
      if (!valid) {
        isValid = false
      }
    }
    return isValid
  }

  fun initNGWordPattern(): Disposable {
    return Observable.create(ObservableOnSubscribe<Pattern> { emitter ->
      val reader = BufferedReader(
          InputStreamReader(mContext.assets.open("ng-word")))
      val buffer = StringBuffer()
      var line: String? = reader.readLine()
      while (line != null) {
        if (buffer.isEmpty()) {
          buffer.append("(").append(line.toLowerCase(Locale.ENGLISH))
        } else {
          buffer.append("|").append(line.toLowerCase(Locale.ENGLISH))
        }
        line = reader.readLine()
      }
      emitter.onNext(Pattern.compile(buffer.append(")").toString()))
    })
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe { pattern -> mNGWordPattern = pattern }
  }

  @ValidMethod(type = intArrayOf(ValidType.NG_WORD))
  fun validateNGWord(str: String?): String? {
    if (mNGWordPattern == null) {
      throw ValidationException(
          "NGWordPattern is null!!! \n Call initNGWordPattern() before call this method!",
          NullPointerException())
    }
    val isValid = !TextUtils.isEmpty(str) && !mNGWordPattern!!.matcher(
        str?.toLowerCase(Locale.ENGLISH))
        .find()
    mMessage = if (isValid) "" else mContext.getString(mAllErrorMessage.get(ValidType.NG_WORD))
    return mMessage
  }

  @ValidMethod(type = intArrayOf(ValidType.VALUE_RANGE_0_100))
  fun validateValueRangeFrom0to100(str: String?): String? {
    val isValid = convertStringToInteger(str) in 0..100
    mMessage = if (isValid)
      ""
    else
      mContext.getString(mAllErrorMessage.get(ValidType.VALUE_RANGE_0_100))
    return mMessage
  }

  @ValidMethod(type = intArrayOf(ValidType.NON_EMPTY))
  fun validateValueNonEmpty(value: String?): String? {
    val isValid = !TextUtils.isEmpty(value)
    mMessage = if (isValid) "" else mContext.getString(mAllErrorMessage.get(ValidType.NON_EMPTY))
    return mMessage
  }

  /**
   * @return Integer.MIN_VALUE if convert error
   */
  private fun convertStringToInteger(s: String?): Int {
    try {
      return Integer.parseInt(s)
    } catch (e: NumberFormatException) {
      return Integer.MIN_VALUE
    }

  }

  /**
   * ValidationException
   */
  private class ValidationException : RuntimeException {
    internal constructor(detailMessage: String) : super(detailMessage)

    internal constructor(detailMessage: String, exception: Throwable) : super(detailMessage,
        exception)
  }

  companion object {
    private val TAG = Validator::class.java.name
  }
}
