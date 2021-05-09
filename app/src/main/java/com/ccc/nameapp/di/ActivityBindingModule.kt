package com.ccc.nameapp.di

import com.ccc.nameapp.di.scopes.ActivityScoped
import com.ccc.nameapp.scenes.login.LoginActivity
import com.ccc.nameapp.scenes.login.LoginModule
import com.ccc.nameapp.scenes.main.MainActivity
import com.ccc.nameapp.scenes.main.MainModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {
    @ActivityScoped
    @ContributesAndroidInjector(modules = [MainModule::class])
    abstract fun contributeMainActivity(): MainActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [LoginModule::class])
    abstract fun contributeLoginActivity(): LoginActivity
}
