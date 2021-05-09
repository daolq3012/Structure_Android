package com.ccc.nameapp.di

import com.ccc.nameapp.di.scopes.FragmentScoped
import com.ccc.nameapp.scenes.login.main.MainLoginFragment
import com.ccc.nameapp.scenes.login.main.MainLoginModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBindingModule {

    @FragmentScoped
    @ContributesAndroidInjector(modules = [MainLoginModule::class])
    abstract fun contributeMainLoginFragment(): MainLoginFragment
}
