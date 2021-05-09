package com.ccc.nameapp.data.source.remote.service

import android.content.Context
import com.ccc.nameapp.BuildConfig
import com.ccc.nameapp.data.source.remote.api.middleware.BooleanAdapter
import com.ccc.nameapp.data.source.remote.api.middleware.IntegerAdapter
import com.ccc.nameapp.data.source.remote.api.middleware.InterceptorImpl
import com.ccc.nameapp.data.source.remote.api.middleware.RxErrorHandlingCallAdapterFactory
import com.ccc.nameapp.di.scopes.AppScoped
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
class NetworkModule {

    @AppScoped
    @Provides
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addCallAdapterFactory(RxErrorHandlingCallAdapterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
    }

    @AppScoped
    @Provides
    fun provideGSONConverter(): Gson {
        val booleanAdapter = BooleanAdapter()
        val integerAdapter = IntegerAdapter()
        return GsonBuilder()
            .registerTypeAdapter(Boolean::class.java, booleanAdapter)
            .registerTypeAdapter(Int::class.java, integerAdapter)
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .excludeFieldsWithoutExposeAnnotation()
            .create()
    }

    @AppScoped
    @Provides
    fun provideInterceptor(): Interceptor {
        return InterceptorImpl.instance
    }

    @AppScoped
    @Provides
    fun provideOKHttpClient(context: Context, interceptor: Interceptor): OkHttpClient {
        val builder = OkHttpClient.Builder()
        val cache = Cache(context.applicationContext.cacheDir, 10 * 1024 * 1024) // 10 MB
        builder.cache(cache)
        builder.addInterceptor(interceptor)
        builder.connectTimeout(CONNECTION_TIMEOUT.toLong(), TimeUnit.SECONDS)
        builder.readTimeout(CONNECTION_TIMEOUT.toLong(), TimeUnit.SECONDS)
        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(logging)
        }
        return builder.build()
    }

    companion object {
        private const val CONNECTION_TIMEOUT = 60
    }
}
