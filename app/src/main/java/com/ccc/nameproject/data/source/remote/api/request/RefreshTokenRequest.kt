package com.ccc.nameproject.data.source.remote.api.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
* Created by daolq on 5/14/18.
* nameproject_Android
*/
class RefreshTokenRequest {
  @SerializedName("refresh_token")
  @Expose
  var refreshToken: String? = null
}
