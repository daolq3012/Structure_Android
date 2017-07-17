package com.fstyle.structure_android

import android.content.Context
import com.fstyle.structure_android.data.source.remote.api.NetworkModule
import com.fstyle.structure_android.data.source.remote.api.service.NameApi
import com.fstyle.structure_android.utils.dagger.AppScope
import com.fstyle.structure_android.utils.rx.BaseSchedulerProvider
import dagger.Component

/**
 * Created by le.quang.dao on 21/03/2017.
 */

@AppScope
@Component(
    modules = arrayOf(ApplicationModule::class, NetworkModule::class))
interface AppComponent {

  //============== Region for Repository ================//

  fun nameApi(): NameApi

  //=============== Region for common ===============//

  fun applicationContext(): Context

  fun baseSchedulerProvider(): BaseSchedulerProvider
}
