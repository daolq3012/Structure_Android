package com.ccc.nameproject.repositories

import com.ccc.nameproject.data.model.Token
import com.ccc.nameproject.data.source.local.sharedprf.SharedPrefsApi
import com.ccc.nameproject.data.source.local.sharedprf.SharedPrefsKey
import com.ccc.nameproject.data.source.remote.api.request.RefreshTokenRequest
import com.ccc.nameproject.data.source.remote.service.nameprojectApi
import io.reactivex.Single

/**
 * Created by daolq on 5/14/18.
 */
interface TokenRepository {

  fun getToken(): Token?

  fun refreshToken(request: RefreshTokenRequest): Single<Token>

  fun saveToken(token: Token)
}

class TokenRepositoryImpl constructor(val sharedPrefsApi: SharedPrefsApi,
    val api: nameprojectApi) : TokenRepository {

  private var tokenCache: Token? = null

  override fun getToken(): Token? {
    return if (tokenCache != null) {
      tokenCache!!
    } else {
      val token = sharedPrefsApi.get(SharedPrefsKey.KEY_TOKEN, Token::class.java)
      if (token.accessToken != null) {
        tokenCache = token
        token
      } else null
    }
  }

  override fun refreshToken(request: RefreshTokenRequest): Single<Token> {
    return api.refreshToken(request)
  }

  override fun saveToken(token: Token) {
    tokenCache = token
    sharedPrefsApi.put(SharedPrefsKey.KEY_TOKEN, token)
  }
}
