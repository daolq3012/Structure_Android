package com.fstyle.structure_android.data.source;

import com.fstyle.structure_android.data.source.local.realm.RealmApi;
import com.fstyle.structure_android.utils.dagger.AppScope;
import com.fstyle.structure_android.utils.rx.BaseSchedulerProvider;
import com.fstyle.structure_android.utils.rx.CustomCompositeSubscription;
import com.fstyle.structure_android.utils.rx.SchedulerProvider;
import dagger.Module;
import dagger.Provides;

/**
 * Created by le.quang.dao on 21/03/2017.
 */

@Module
public class RepositoryModule {

    @AppScope
    @Provides
    public RealmApi provideRealmApi() {
        return new RealmApi();
    }

    @Provides
    @AppScope
    public CustomCompositeSubscription provideCompositeSubscription() {
        return new CustomCompositeSubscription();
    }

    @Provides
    @AppScope
    public BaseSchedulerProvider provideBaseSchedulerProvider() {
        return SchedulerProvider.getInstance();
    }
}
