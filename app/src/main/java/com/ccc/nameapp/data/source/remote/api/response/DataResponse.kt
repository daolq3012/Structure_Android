package com.ccc.nameapp.data.source.remote.api.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class DataResponse<out T> {
    @Expose
    @SerializedName("data")
    val dataResponse: T? = null
}
