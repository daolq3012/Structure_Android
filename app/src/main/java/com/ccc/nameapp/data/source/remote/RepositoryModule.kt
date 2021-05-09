package com.ccc.nameapp.data.source.remote

import com.ccc.nameapp.data.source.local.sharedprf.SharedPrefsApi
import com.ccc.nameapp.data.source.remote.api.middleware.InterceptorImpl
import com.ccc.nameapp.data.source.remote.service.RestfulApi
import com.ccc.nameapp.repositories.TokenRepository
import com.ccc.nameapp.repositories.TokenRepositoryImpl
import com.ccc.nameapp.repositories.UserRepository
import com.ccc.nameapp.repositories.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module(includes = [RepositoryModule.RepositoryAbstractModule::class])
class RepositoryModule {

    @Provides
    fun provideTokenRepository(sharedPrf: SharedPrefsApi, api: RestfulApi): TokenRepository {
        val tokenRepository = TokenRepositoryImpl(sharedPrf, api)
        InterceptorImpl.instance.setTokenRepository(tokenRepository)
        return tokenRepository
    }

    @Module
    interface RepositoryAbstractModule {
        @Binds
        fun bindUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository
    }
}
