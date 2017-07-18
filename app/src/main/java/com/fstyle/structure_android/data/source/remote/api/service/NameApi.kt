package com.fstyle.structure_android.data.source.remote.api.service

import com.fstyle.structure_android.data.model.User
import com.fstyle.structure_android.data.source.remote.api.response.SearchUserResponse
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by le.quang.dao on 10/03/2017.
 */

interface NameApi {
  @GET("/search/users")
  fun searchGithubUsers(@Query("per_page") limit: Int,
      @Query("q") searchTerm: String?): Single<SearchUserResponse>

  @GET("/users/{username}")
  fun getUser(@Path("username") username: String?): Single<User>
}
