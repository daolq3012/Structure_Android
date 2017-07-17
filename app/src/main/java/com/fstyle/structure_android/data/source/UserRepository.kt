package com.fstyle.structure_android.data.source

import com.fstyle.structure_android.data.model.User
import com.fstyle.structure_android.data.source.local.sqlite.UserLocalDataSource
import com.fstyle.structure_android.data.source.remote.UserRemoteDataSource
import io.reactivex.Single

/**
 * Created by le.quang.dao on 10/03/2017.
 */

class UserRepository(val localDataSource: UserLocalDataSource,
    val remoteDataSource: UserRemoteDataSource) {

  fun searchUsers(keyword: String, limit: Int): Single<List<User>> {
    return remoteDataSource.searchUsers(keyword, limit)
  }
}
