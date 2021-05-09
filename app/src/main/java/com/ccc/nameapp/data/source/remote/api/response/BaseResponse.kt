package com.ccc.nameapp.data.source.remote.api.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

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
