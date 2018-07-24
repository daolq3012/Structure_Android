package com.ccc.nameproject.data.source.remote.service

import com.ccc.nameproject.data.model.Token
import com.ccc.nameproject.data.source.remote.api.request.RefreshTokenRequest
import com.ccc.nameproject.data.source.remote.api.response.BaseResponse
import com.ccc.nameproject.data.source.remote.api.response.LoginResponse
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * Created by daolq on 5/14/18.
 */

interface nameprojectApi {
  @POST("/api/v0/refresh-token")
  fun refreshToken(@Body refreshTokenRequest: RefreshTokenRequest?): Single<Token>

  @POST("/api/v1/login")
  @FormUrlEncoded
  fun login(@Field("email") email: String, @Field(
      "password") password: String): Single<BaseResponse<LoginResponse>>
}
