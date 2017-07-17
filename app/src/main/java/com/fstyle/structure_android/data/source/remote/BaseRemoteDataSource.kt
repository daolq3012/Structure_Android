package com.fstyle.structure_android.data.source.remote

import com.fstyle.structure_android.data.source.remote.api.service.NameApi

/**
 * Created by le.quang.dao on 10/03/2017.
 */

abstract class BaseRemoteDataSource(internal var nameApi: NameApi) {
  fun getNameApi(): NameApi {
    return nameApi
  }
}
