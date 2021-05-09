package com.ccc.nameapp.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Token(
    @Expose
    @SerializedName("accessToken")
    var accessToken: String? = null,
    @Expose
    @SerializedName("accessTokenExpiresAt")
    var accessTokenExpiresAt: String? = null,
    @Expose
    @SerializedName("refreshToken")
    var refreshToken: String? = null,
    @Expose
    @SerializedName("refreshTokenExpiresAt")
    var refreshTokenExpiresAt: String? = null
)
