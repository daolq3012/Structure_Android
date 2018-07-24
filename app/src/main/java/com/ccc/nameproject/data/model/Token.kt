package com.ccc.nameproject.data.model

import com.google.gson.annotations.Expose

/**
 * Created by daolq on 5/14/18.
 */
class Token {
  @Expose
  var tokenType: String? = null
  @Expose
  var accessToken: String? = null
  @Expose
  var refreshToken: String? = null
}
