package com.ccc.nameproject

import android.app.Application
import com.ccc.nameproject.data.source.RepositoryModule

/**
 * Created by daolq on 5/15/18.
 * nameproject_Android
 */
class MainApplication : Application() {

  lateinit var appComponent: AppComponent

  override fun onCreate() {
    super.onCreate()
    appComponent = DaggerAppComponent.builder().applicationModule(
        ApplicationModule(applicationContext)).repositoryModule(
        RepositoryModule()).build()
  }
}
