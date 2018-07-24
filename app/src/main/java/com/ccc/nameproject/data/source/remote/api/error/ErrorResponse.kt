package com.ccc.nameproject.data.source.remote.api.error

import com.ccc.nameproject.utils.Constant
import com.ccc.nameproject.utils.StringUtils
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by MyPC on 30/10/2017.
 */

class ErrorResponse {

  @Expose
  @SerializedName("error")
  private val mError: Error? = null

  val error: Error
    get() = mError ?: Error()

  class Error {
    @Expose
    @SerializedName("code")
    val code: Int = 0
    @Expose
    @SerializedName("description")
    private val messages: List<String>? = null

    val message: String?
      get() = if (messages == null || messages.isEmpty()) {
        ""
      } else StringUtils.convertStringToListStringWithFormatPattern(messages,
          Constant.ENTER_SPACE_FORMAT)
  }
}
