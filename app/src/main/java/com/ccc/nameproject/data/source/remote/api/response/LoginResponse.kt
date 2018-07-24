package com.ccc.nameproject.data.source.remote.api.response

import com.ccc.nameproject.data.model.Token
import com.ccc.nameproject.data.model.User
import com.google.gson.annotations.Expose

class LoginResponse {
  @Expose
  var token:Token? = null
  @Expose
  var user: User? = null
}
