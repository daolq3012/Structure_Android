package com.fstyle.structure_android;

import android.app.Application;

import com.fstyle.structure_android.data.source.RepositoryModule;
import com.fstyle.structure_android.data.source.remote.api.NetworkModule;

/**
 * Created by le.quang.dao on 10/03/2017.
 */

public class MainApplication extends Application {

    private AppComponent mAppComponent;

    public AppComponent getAppComponent() {
        if (mAppComponent == null) {
            mAppComponent = DaggerAppComponent.builder()
                    .applicationModule(new ApplicationModule(getApplicationContext()))
                    .networkModule(new NetworkModule(this))
                    .repositoryModule(new RepositoryModule())
                    .build();
        }
        return mAppComponent;
    }
}
