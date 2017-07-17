package com.fstyle.structure_android.data.source.remote.api.error

import android.support.annotation.NonNull
import io.reactivex.functions.Consumer

/**
 * Created by Sun on 4/16/2017.
 */

/**
 * Created by Sun on 4/16/2017.
 */

abstract class RequestError : Consumer<Throwable> {

  /**
   * Don't override this method.
   * Override [RequestError.onRequestError] instead
   */
  @Throws(Exception::class)
  override fun accept(@NonNull throwable: Throwable) {
    if (throwable is BaseException) {
      onRequestError(throwable)
    } else {
      onRequestError(BaseException.toUnexpectedError(throwable))
    }
  }

  abstract fun onRequestError(error: BaseException)
}
