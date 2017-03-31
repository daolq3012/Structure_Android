package com.fstyle.structure_android;

import android.content.Context;
import com.fstyle.structure_android.data.source.local.sharedprf.SharedPrefsApi;
import com.fstyle.structure_android.data.source.local.sharedprf.SharedPrefsImpl;
import com.fstyle.structure_android.utils.dagger.AppScope;
import com.fstyle.structure_android.utils.rx.BaseSchedulerProvider;
import com.fstyle.structure_android.utils.rx.SchedulerProvider;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Sun on 3/18/2017.
 */

@Module
public class ApplicationModule {

    private Context mContext;

    public ApplicationModule(Context mContext) {
        this.mContext = mContext;
    }

    @Provides
    @AppScope
    public Context provideApplicationContext() {
        return mContext;
    }

    @Provides
    @AppScope
    public SharedPrefsApi provideSharedPrefsApi() {
        return new SharedPrefsImpl(mContext);
    }

    @Provides
    @AppScope
    public BaseSchedulerProvider provideBaseSchedulerProvider() {
        return SchedulerProvider.getInstance();
    }
}
