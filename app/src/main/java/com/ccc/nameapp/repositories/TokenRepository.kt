package com.ccc.nameapp.repositories

import com.ccc.nameapp.data.model.Token
import com.ccc.nameapp.data.source.local.sharedprf.SharedPrefsApi
import com.ccc.nameapp.data.source.local.sharedprf.SharedPrefsKey
import com.ccc.nameapp.data.source.remote.api.request.RefreshTokenRequest
import com.ccc.nameapp.data.source.remote.service.RestfulApi
import com.ccc.nameapp.di.scopes.AppScoped
import io.reactivex.Single

interface TokenRepository {
    fun getToken(): Token?
    fun refreshToken(request: RefreshTokenRequest): Single<Token>
    fun saveToken(token: Token)
    fun clearToken()
}

@AppScoped
class TokenRepositoryImpl(private val sharedPrefsApi: SharedPrefsApi, val api: RestfulApi) : TokenRepository {

    override fun getToken(): Token? {
        return sharedPrefsApi.get(SharedPrefsKey.KEY_TOKEN, Token::class.java)
    }

    override fun refreshToken(request: RefreshTokenRequest): Single<Token> {
        return api.refreshToken(request)
    }

    override fun saveToken(token: Token) {
        sharedPrefsApi.put(SharedPrefsKey.KEY_TOKEN, token)
    }

    override fun clearToken() {
        sharedPrefsApi.clearKey(SharedPrefsKey.KEY_TOKEN)
    }
}
