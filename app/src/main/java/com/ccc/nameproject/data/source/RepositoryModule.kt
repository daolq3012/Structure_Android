package com.ccc.nameproject.data.source

import com.ccc.nameproject.data.source.local.sharedprf.SharedPrefsApi
import com.ccc.nameproject.data.source.remote.api.middleware.InterceptorImpl
import com.ccc.nameproject.data.source.remote.service.nameprojectApi
import com.ccc.nameproject.repositories.TokenRepository
import com.ccc.nameproject.repositories.TokenRepositoryImpl
import com.ccc.nameproject.repositories.UserRepository
import com.ccc.nameproject.repositories.UserRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by daolq on 5/22/18.
 * nameproject_Android
 */

@Module
class RepositoryModule {

  @Provides
  @Singleton
  fun provideTokenRepository(sharedPrefsApi: SharedPrefsApi,
      nameprojectApi: nameprojectApi): TokenRepository {
    val tokenRepository = TokenRepositoryImpl(sharedPrefsApi = sharedPrefsApi, api = nameprojectApi)
    InterceptorImpl.instance.setTokenRepository(tokenRepository)
    return tokenRepository
  }

  @Provides
  @Singleton
  fun provideUserRepository(sharedPrefsApi: SharedPrefsApi,
      nameprojectApi: nameprojectApi): UserRepository {
    return UserRepositoryImpl(sharedPrefsApi = sharedPrefsApi, api = nameprojectApi)
  }
}
