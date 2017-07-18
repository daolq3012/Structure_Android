package com.fstyle.structure_android.data.source.remote

import com.fstyle.structure_android.data.model.User
import com.fstyle.structure_android.data.source.UserDataSource
import com.fstyle.structure_android.data.source.remote.api.service.NameApi
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by le.quang.dao on 10/03/2017.
 */

class UserRemoteDataSource @Inject
constructor(nameApi: NameApi) : BaseRemoteDataSource(nameApi), UserDataSource.RemoteDataSource {

  override fun searchUsers(keyWord: String, limit: Int): Single<List<User>> {
    return nameApi.searchGithubUsers(limit, keyWord)
        .map { searchUserResponse -> searchUserResponse.users }
  }

  override fun getUserDetailFromServer(userLogin: String?): Single<User> {
    return nameApi.getUser(userLogin)
  }
}
