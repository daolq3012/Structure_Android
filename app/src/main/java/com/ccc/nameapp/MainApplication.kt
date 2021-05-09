package com.ccc.nameapp

import android.os.StrictMode
import androidx.appcompat.app.AppCompatDelegate
import com.bumptech.glide.Glide
import com.ccc.nameapp.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class MainApplication : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)

        if (BuildConfig.DEBUG) {
            enabledStrictMode()
        }
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().application(this).build()
    }

    override fun onTrimMemory(level: Int) {
        Glide.get(this).onTrimMemory(level)
        super.onTrimMemory(level)
    }

    override fun onLowMemory() {
        Glide.get(this).onLowMemory()
        super.onLowMemory()
    }

    private fun enabledStrictMode() {
        StrictMode.setThreadPolicy(
            StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .penaltyDeath()
                .build()
        )
    }
}
