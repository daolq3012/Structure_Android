package com.fstyle.structure_android;

import android.content.Context;
import com.fstyle.structure_android.data.source.RepositoryModule;
import com.fstyle.structure_android.data.source.remote.api.NetworkModule;
import com.fstyle.structure_android.data.source.remote.api.service.NameApi;
import com.fstyle.structure_android.utils.dagger.AppScope;
import com.fstyle.structure_android.utils.rx.BaseSchedulerProvider;
import dagger.Component;

/**
 * Created by le.quang.dao on 21/03/2017.
 */

@AppScope
@Component(modules = { ApplicationModule.class, NetworkModule.class, RepositoryModule.class })
public interface AppComponent {

    //============== Region for Repository ================//

    NameApi nameApi();

    //=============== Region for common ===============//

    Context applicationContext();

    BaseSchedulerProvider baseSchedulerProvider();
}
