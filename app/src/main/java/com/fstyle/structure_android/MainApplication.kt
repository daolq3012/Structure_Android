package com.fstyle.structure_android

import android.app.Application
import com.fstyle.structure_android.data.source.remote.api.NetworkModule

/**
 * Created by le.quang.dao on 10/03/2017.
 */

class MainApplication : Application() {

  var appComponent: AppComponent? = null

  override fun onCreate() {
    super.onCreate()
    appComponent = DaggerAppComponent.builder()
        .applicationModule(ApplicationModule(applicationContext))
        .networkModule(NetworkModule(this))
        .build()
  }
}
