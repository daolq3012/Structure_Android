package com.ccc.nameproject

import android.content.Context
import com.ccc.nameproject.data.source.RepositoryModule
import com.ccc.nameproject.repositories.TokenRepository
import com.ccc.nameproject.scenes.login.LoginActivity
import com.ccc.nameproject.utils.rx.BaseSchedulerProvider
import dagger.Component
import javax.inject.Singleton

/**
 * Created by le.quang.dao on 21/03/2017.
 */

@Singleton
@Component(
    modules = [ApplicationModule::class, RepositoryModule::class])
interface AppComponent {

  //============== Region for Repository ================//

  fun tokenRepository(): TokenRepository

  //=============== Region for common ===============//

  fun applicationContext(): Context

  fun baseSchedulerProvider(): BaseSchedulerProvider


  fun inject(loginActivity: LoginActivity)
}
