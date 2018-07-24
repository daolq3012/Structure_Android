package com.ccc.nameproject.data.source.remote.service

import android.content.Context
import com.ccc.nameproject.BuildConfig
import com.ccc.nameproject.data.source.remote.api.middleware.BooleanAdapter
import com.ccc.nameproject.data.source.remote.api.middleware.IntegerAdapter
import com.ccc.nameproject.data.source.remote.api.middleware.InterceptorImpl
import com.ccc.nameproject.data.source.remote.api.middleware.RxErrorHandlingCallAdapterFactory
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by daolq on 5/14/18.
 */

object nameprojectService {
  private const val CONNECTION_TIMEOUT = 60

  fun getInstanceService(context: Context): nameprojectApi {
    val gson = configGSONConverter()
    val httpClient = configOKHttpClient(context)
    val retrofit = configRetrofit(gson, httpClient)
    return retrofit.create(nameprojectApi::class.java)
  }

  private fun configRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addCallAdapterFactory(RxErrorHandlingCallAdapterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(okHttpClient)
        .build()
  }

  private fun configGSONConverter(): Gson {
    val booleanAdapter = BooleanAdapter()
    val integerAdapter = IntegerAdapter()

    return GsonBuilder()
        .registerTypeAdapter(Boolean::class.java, booleanAdapter)
        .registerTypeAdapter(Int::class.java, integerAdapter)
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .excludeFieldsWithoutExposeAnnotation()
        .create()
  }

  private fun configOKHttpClient(context: Context): OkHttpClient {
    val builder = OkHttpClient.Builder()
    val cache = Cache(context.applicationContext.cacheDir, 10 * 1024 * 1024) // 10 MB
    builder.cache(cache)
    builder.addInterceptor(InterceptorImpl.instance)
    builder.connectTimeout(CONNECTION_TIMEOUT.toLong(), TimeUnit.SECONDS)
    builder.readTimeout(CONNECTION_TIMEOUT.toLong(), TimeUnit.SECONDS)
    if (BuildConfig.DEBUG) {
      val logging = HttpLoggingInterceptor()
      logging.level = HttpLoggingInterceptor.Level.BODY
      builder.addInterceptor(logging)
    }
    return builder.build()
  }
}
