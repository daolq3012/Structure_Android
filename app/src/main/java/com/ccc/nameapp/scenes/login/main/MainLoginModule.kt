package com.ccc.nameapp.scenes.login.main

import com.ccc.nameapp.di.scopes.FragmentScoped
import dagger.Binds
import dagger.Module

@Module(includes = [MainLoginModule.MainLoginAbstractModule::class])
class MainLoginModule {

    @Module
    interface MainLoginAbstractModule {
        @FragmentScoped
        @Binds
        fun bindMainLoginView(fragment: MainLoginFragment): MainLoginView

        @FragmentScoped
        @Binds
        fun bindMainLoginPresenter(presenter: MainLoginPresenterImpl): MainLoginPresenter

        @FragmentScoped
        @Binds
        fun bindMainLoginNavigator(navigator: MainLoginNavigatorImpl): MainLoginNavigator
    }
}
