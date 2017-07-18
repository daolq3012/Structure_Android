package com.fstyle.structure_android.data.source

import com.fstyle.structure_android.data.model.User
import com.fstyle.structure_android.data.source.remote.UserRemoteDataSource
import io.reactivex.Single

/**
 * Created by le.quang.dao on 10/03/2017.
 */

interface UserRepository : UserDataSource.RemoteDataSource

class UserRepositoryImpl(val remoteDataSource: UserRemoteDataSource) : UserRepository {

  override fun searchUsers(keyword: String, limit: Int): Single<List<User>> {
    return remoteDataSource.searchUsers(keyword, limit)
  }

  override fun getUserDetailFromServer(userLogin: String?): Single<User> {
    return remoteDataSource.getUserDetailFromServer(userLogin)
  }
}
