package com.ccc.nameproject

import android.content.Context
import com.ccc.nameproject.data.source.local.sharedprf.SharedPrefsApi
import com.ccc.nameproject.data.source.local.sharedprf.SharedPrefsImpl
import com.ccc.nameproject.data.source.remote.service.nameprojectApi
import com.ccc.nameproject.data.source.remote.service.nameprojectService
import com.ccc.nameproject.utils.rx.BaseSchedulerProvider
import com.ccc.nameproject.utils.rx.SchedulerProvider
import com.ccc.nameproject.utils.validator.Validator
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by le.quang.dao on 21/03/2017.
 */

@Module
class ApplicationModule(private val mContext: Context) {

  @Provides
  @Singleton
  fun provideApplicationContext(): Context {
    return mContext
  }

  @Provides
  @Singleton
  fun provideSharedPrefsApi(): SharedPrefsApi {
    return SharedPrefsImpl(mContext)
  }

  @Provides
  @Singleton
  fun providenameprojectApi(): nameprojectApi {
    return nameprojectService.getInstanceService(mContext)
  }

  @Provides
  @Singleton
  fun provideBaseSchedulerProvider(): BaseSchedulerProvider {
    return SchedulerProvider.instance
  }

  @Provides
  @Singleton
  fun provideValidator(): Validator {
    return Validator(mContext)
  }
}
