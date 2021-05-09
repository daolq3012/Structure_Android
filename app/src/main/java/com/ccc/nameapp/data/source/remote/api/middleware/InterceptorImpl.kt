package com.ccc.nameapp.data.source.remote.api.middleware

import android.util.Log
import com.ccc.nameapp.data.model.Token
import com.ccc.nameapp.data.source.remote.api.request.RefreshTokenRequest
import com.ccc.nameapp.repositories.TokenRepository
import com.ccc.nameapp.utils.DateUtils
import io.reactivex.observers.DisposableSingleObserver
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.net.HttpURLConnection
import java.util.Locale

class InterceptorImpl : Interceptor {

    private var mTokenRepository: TokenRepository? = null

    companion object {
        val instance: InterceptorImpl by lazy { InterceptorImpl() }

        const val KEY_ACCESS_TOKEN_HEADER = "access_token"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = initializeHeader(chain)
        var request = builder.build()
        var response = chain.proceed(request)
        if (response.code() == HttpURLConnection.HTTP_UNAUTHORIZED || isAccessTokenExpired()) {
            mTokenRepository?.clearToken()
            refreshToken()
            builder.removeHeader(KEY_ACCESS_TOKEN_HEADER)
            mTokenRepository?.getToken()?.accessToken?.let {
                builder.addHeader(KEY_ACCESS_TOKEN_HEADER, it)
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
        return originRequest.newBuilder()
            .header("Content-Type", "application/x-www-form-urlencoded")
            .addHeader("Cache-Control", "no-cache")
            .addHeader("Cache-Control", "no-store")
            .addHeader("Authorization", "Bearer ${mTokenRepository?.getToken()?.accessToken}")
            .addHeader("Accept-Language", Locale.getDefault().language)
            .method(originRequest.method(), originRequest.body())
    }

    private fun refreshToken() {
        val tokenRequest = RefreshTokenRequest()
        tokenRequest.refreshToken = mTokenRepository?.getToken()?.refreshToken

        tokenRequest.let { refreshTokenRequest ->
            mTokenRepository?.refreshToken(refreshTokenRequest)
                ?.subscribe(object : DisposableSingleObserver<Token>() {
                    override fun onSuccess(token: Token) {
                        mTokenRepository?.saveToken(token)
                    }

                    override fun onError(e: Throwable) {
                        Log.w("InterceptorImpl", e)
                    }
                })
        }
    }

    private fun isAccessTokenExpired(): Boolean {
        val expiredTime = mTokenRepository?.getToken()?.accessTokenExpiresAt ?: return false
        val dateExpired = DateUtils.convertStringToDate(expiredTime, DateUtils.SERVER_DATE_PATTERN) ?: return false
        return dateExpired.before(DateUtils.getCurrentDate())
    }
}
