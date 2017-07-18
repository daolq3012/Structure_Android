package com.fstyle.structure_android.screen.userdetail;

import com.fstyle.structure_android.AppComponent;
import com.fstyle.structure_android.utils.dagger.ActivityScope;

import dagger.Component;

/**
 * This is a Dagger component. Refer to {@link com.fstyle.structure_android.MainApplication} for the list of Dagger components
 * used in this application.
 */
@ActivityScope
@Component(dependencies = arrayOf(AppComponent::class),
    modules = arrayOf(UserDetailModule::class))
public interface UserDetailComponent {
  fun inject(userDetailActivity: UserDetailActivity)
}
