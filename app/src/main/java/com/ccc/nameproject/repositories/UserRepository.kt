package com.ccc.nameproject.repositories

import com.ccc.nameproject.data.model.Token
import com.ccc.nameproject.data.model.User
import com.ccc.nameproject.data.source.local.sharedprf.SharedPrefsApi
import com.ccc.nameproject.data.source.remote.service.nameprojectApi
import io.reactivex.Single

/**
 * Created by daolq on 5/17/18.
 * nameproject_Android
 */

interface UserRepository {
  fun login(email: String, password: String): Single<Token>
}

class UserRepositoryImpl constructor(val sharedPrefsApi: SharedPrefsApi,
    val api: nameprojectApi) : UserRepository {

  private var userCache: User? = null

  override fun login(email: String, password: String): Single<Token> {
    return api.login(email, password).map { loginResponse ->
      userCache = loginResponse.data?.user
      loginResponse.data?.token
    }
  }
}
