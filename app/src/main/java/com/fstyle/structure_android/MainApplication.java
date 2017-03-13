package com.fstyle.structure_android;

import android.app.Application;
import com.fstyle.structure_android.data.source.local.realm.DataLocalMigration;
import com.fstyle.structure_android.data.source.remote.api.service.NameServiceClient;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by le.quang.dao on 10/03/2017.
 */

public class MainApplication extends Application {

    private static final String REALM_SCHEMA_NAME = "android_structure.realm";
    private static final int REALM_SCHEMA_VERSION = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        NameServiceClient.initialize(this);
        initAndMigrateRealmIfNeeded();
    }

    private void initAndMigrateRealmIfNeeded() {
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder().name(REALM_SCHEMA_NAME)
                .schemaVersion(REALM_SCHEMA_VERSION)
                .migration(new DataLocalMigration())
                .build();
        Realm.setDefaultConfiguration(config);
        Realm realm = Realm.getDefaultInstance(); // Automatically run migration if needed
        realm.close();
    }
}
