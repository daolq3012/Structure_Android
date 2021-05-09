package com.ccc.nameapp.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class User(
    @Expose
    @SerializedName("_id")
    var id: String,
    @Expose
    @SerializedName("name")
    var name: String,
    @Expose
    @SerializedName("email")
    var email: String = "",
    @Expose
    @SerializedName("avatar")
    var avatar: String = ""
)
