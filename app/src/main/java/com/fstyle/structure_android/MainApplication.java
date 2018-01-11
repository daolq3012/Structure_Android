package com.fstyle.structure_android;

import android.app.Application;
import com.fstyle.structure_android.data.source.local.config.sqlite.UserDbHelper;
import com.fstyle.structure_android.data.source.remote.config.service.NameServiceClient;
/**
 * Created by le.quang.dao on 10/03/2017.
 */

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        NameServiceClient.initialize(this);
        UserDbHelper.initializeInstance(this);
    }
}
