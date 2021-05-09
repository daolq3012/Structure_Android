package com.ccc.nameapp.data.source.remote.api.error

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ErrorResponse {
    @Expose
    @SerializedName("message")
    val message: String? = null
}
