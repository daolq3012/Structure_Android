package com.fstyle.structure_android.data.source.remote.api;

import android.app.Application;

import com.fstyle.structure_android.BuildConfig;
import com.fstyle.structure_android.data.source.remote.api.middleware.InterceptorImpl;
import com.fstyle.structure_android.data.source.remote.api.middleware.RxErrorHandlingCallAdapterFactory;
import com.fstyle.structure_android.data.source.remote.api.service.BooleanAdapter;
import com.fstyle.structure_android.data.source.remote.api.service.IntegerAdapter;
import com.fstyle.structure_android.data.source.remote.api.service.NameApi;
import com.fstyle.structure_android.utils.Constant;
import com.fstyle.structure_android.utils.dagger.AppScope;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Sun on 3/18/2017.
 */

@Module
public class NetworkModule {
    private static final int CONNECTION_TIMEOUT = 60;

    private Application mApplication;

    public NetworkModule(Application mApplication) {
        this.mApplication = mApplication;
    }

    @AppScope
    @Provides
    public Application provideApplication() {
        return mApplication;
    }

    @AppScope
    @Provides
    public Gson provideGson() {
        BooleanAdapter booleanAdapter = new BooleanAdapter();
        IntegerAdapter integerAdapter = new IntegerAdapter();
        return new GsonBuilder()
                .registerTypeAdapter(Boolean.class, booleanAdapter)
                .registerTypeAdapter(boolean.class, booleanAdapter)
                .registerTypeAdapter(Integer.class, integerAdapter)
                .registerTypeAdapter(int.class, integerAdapter)
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .excludeFieldsWithoutExposeAnnotation()
                .create();
    }

    @AppScope
    @Provides
    public Cache provideOkHttpCache(Application application) {
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        return new Cache(application.getCacheDir(), cacheSize);
    }

    @AppScope
    @Provides
    public Interceptor provideInterceptor() {
        return new InterceptorImpl();
    }

    @AppScope
    @Provides
    public OkHttpClient provideOkHttpClient(Cache cache, Interceptor interceptor) {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.cache(cache);
        httpClientBuilder.addInterceptor(interceptor);
        httpClientBuilder.readTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS);
        httpClientBuilder.connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS);
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            httpClientBuilder.addInterceptor(logging);
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        }
        return httpClientBuilder.build();
    }

    @AppScope
    @Provides
    public Retrofit provideRetrofit(Gson gson, OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(Constant.END_POINT_URL)
                .addCallAdapterFactory(RxErrorHandlingCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    @AppScope
    @Provides
    public NameApi provideNameApi(Retrofit retrofit) {
        return retrofit.create(NameApi.class);
    }
}
