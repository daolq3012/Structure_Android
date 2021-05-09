package com.ccc.nameapp.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.ccc.nameapp.data.source.local.room.JobChatLocalDatabase
import com.ccc.nameapp.data.source.local.sharedprf.SharedPrefsApi
import com.ccc.nameapp.data.source.local.sharedprf.SharedPrefsImpl
import com.ccc.nameapp.data.source.remote.service.RestfulApi
import com.ccc.nameapp.di.scopes.AppScoped
import com.ccc.nameapp.utils.Validator
import com.ccc.nameapp.utils.rx.SchedulerProvider
import com.ccc.nameapp.utils.rx.SchedulerProviderImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module(includes = [AppModule.AppAbstractModule::class])
class AppModule {

    @AppScoped
    @Provides
    fun provideRoomDatabase(context: Context): JobChatLocalDatabase {
        return Room.databaseBuilder(context, JobChatLocalDatabase::class.java, NAME_APP_DB).build()
    }

    @AppScoped
    @Provides
    fun provideRestfulService(retrofit: Retrofit): RestfulApi {
        return retrofit.create(RestfulApi::class.java)
    }

    @AppScoped
    @Provides
    fun provideValidator(context: Context): Validator {
        return Validator(context)
    }

    @Module
    interface AppAbstractModule {
        @AppScoped
        @Binds
        fun bindApplicationContext(application: Application): Context

        @AppScoped
        @Binds
        fun bindSchedulerProvider(schedulerProvider: SchedulerProviderImpl): SchedulerProvider

        @AppScoped
        @Binds
        fun bindSharedPreference(sharedPrefs: SharedPrefsImpl): SharedPrefsApi
    }

    companion object {
        private const val NAME_APP_DB = "nameapp.db"
    }
}
