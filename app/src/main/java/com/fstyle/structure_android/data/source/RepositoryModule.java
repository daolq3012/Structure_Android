package com.fstyle.structure_android.data.source;

import com.fstyle.structure_android.data.source.local.realm.RealmApi;
import com.fstyle.structure_android.utils.dagger.AppScope;
import dagger.Module;
import dagger.Provides;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Sun on 3/18/2017.
 */

@Module
public class RepositoryModule {

    @AppScope
    @Provides
    public RealmApi provideRealmApi() {
        return new RealmApi();
    }

    @AppScope
    @Provides
    public CompositeSubscription provideCompositeSubscription() {
        return new CompositeSubscription();
    }
}
