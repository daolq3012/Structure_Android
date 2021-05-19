package com.ccc.nameapp.data.source.remote.service

import com.ccc.nameapp.data.model.Token
import com.ccc.nameapp.data.source.remote.api.request.RefreshTokenRequest
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface RestfulApi {
    @POST("/api/v0/refresh-token")
    fun refreshToken(@Body refreshTokenRequest: RefreshTokenRequest?): Single<Token>

    @POST("/oauth/token")
    @FormUrlEncoded
    fun loginWithEmailAndPassword(
        @Field("username") username: String,
        @Field("password") password: String
    ): Single<Token>
}
