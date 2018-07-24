package com.ccc.nameproject.data.source.remote.api.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by daolq on 5/14/18.
 */
class BaseResponse<T> {

  @Expose
  @SerializedName("data")
  var data: T? = null
  @Expose
  @SerializedName("code")
  var code: Int? = null
  @Expose
  @SerializedName("message")
  var message: String? = null
}