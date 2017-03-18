package com.fstyle.structure_android.screen.main;

import com.fstyle.structure_android.AppComponent;
import com.fstyle.structure_android.utils.dagger.ActivityScope;

import dagger.Component;

/**
 * Created by Sun on 3/18/2017.
 */

@ActivityScope
@Component(dependencies = AppComponent.class, modules = MainModule.class)
public interface MainComponent {
    void inject(MainActivity mainActivity);
}
