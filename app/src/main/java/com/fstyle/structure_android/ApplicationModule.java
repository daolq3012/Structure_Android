package com.fstyle.structure_android;

import android.content.Context;
import com.fstyle.structure_android.data.source.local.sharedprf.SharedPrefsApi;
import com.fstyle.structure_android.data.source.local.sharedprf.SharedPrefsImpl;
import com.fstyle.structure_android.utils.dagger.AppScope;
import dagger.Module;
import dagger.Provides;

/**
 * Created by le.quang.dao on 21/03/2017.
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
}
