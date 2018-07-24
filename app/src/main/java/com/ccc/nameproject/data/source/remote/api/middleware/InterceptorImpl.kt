package com.ccc.nameproject.data.source.remote.api.middleware

import android.util.Log
import com.ccc.nameproject.data.model.Token
import com.ccc.nameproject.data.source.remote.api.request.RefreshTokenRequest
import com.ccc.nameproject.repositories.TokenRepository
import io.reactivex.observers.DisposableSingleObserver
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.net.HttpURLConnection

/**
* Created by daolq on 5/14/18.
* nameproject_Android
*/
class InterceptorImpl : Interceptor {

  private val KEY_TOKEN = "Authorization"

  private var mTokenRepository: TokenRepository? = null

  companion object {
    val instance: InterceptorImpl by lazy { InterceptorImpl() }
  }

  override fun intercept(chain: Interceptor.Chain): Response {
    val builder = initializeHeader(chain)
    var request = builder.build()
    var response = chain.proceed(request)
    if (response.code() == HttpURLConnection.HTTP_UNAUTHORIZED) {
      refreshToken()
      builder.removeHeader(KEY_TOKEN)
      mTokenRepository?.getToken()?.accessToken.let {
        builder.addHeader(KEY_TOKEN, it!!)
      }
      request = builder.build()
      response = chain.proceed(request)
    }
    return response
  }

  fun setTokenRepository(tokenRepository: TokenRepository) {
    mTokenRepository = tokenRepository
  }

  private fun initializeHeader(chain: Interceptor.Chain): Request.Builder {
    val originRequest = chain.request()
    val requestBuilder = originRequest.newBuilder()
        .header("Accept", "application/json")
        .addHeader("Cache-Control", "no-cache")
        .addHeader("Cache-Control", "no-store")
        .method(originRequest.method(), originRequest.body())
        mTokenRepository?.getToken()?.accessToken.let {
          requestBuilder.addHeader(KEY_TOKEN, it!!)
        }
    return requestBuilder
  }

  private fun refreshToken() {
    val tokenRequest = RefreshTokenRequest()
    tokenRequest.refreshToken = mTokenRepository?.getToken()?.refreshToken

    mTokenRepository?.refreshToken(tokenRequest)!!.subscribe(object : DisposableSingleObserver<Token>() {
      override fun onSuccess(token: Token) {
        mTokenRepository?.saveToken(token)
      }

      override fun onError(e: Throwable) {
        Log.w("InterceptorImpl", e)
      }
    })
  }
}