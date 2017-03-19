package com.fstyle.structure_android;

import android.content.Context;

import com.fstyle.structure_android.data.source.RepositoryModule;
import com.fstyle.structure_android.data.source.UserRepository;
import com.fstyle.structure_android.data.source.remote.api.NetworkModule;
import com.fstyle.structure_android.utils.dagger.AppScope;

import dagger.Component;

/**
 * Created by Sun on 3/18/2017.
 */

@AppScope
@Component(modules = {ApplicationModule.class, NetworkModule.class, RepositoryModule.class})
public interface AppComponent {

    //============== Region for Repository ================//

    UserRepository userRepository();

    //=============== Region for common ===============//

    Context applicationContext();
}
