package com.ccc.nameapp.scenes.main

import com.ccc.nameapp.di.scopes.ActivityScoped
import dagger.Binds
import dagger.Module

@Module(includes = [MainModule.MainAbstractModule::class])
abstract class MainModule {

    @Module
    interface MainAbstractModule {
        @ActivityScoped
        @Binds
        fun bindMainView(mainActivity: MainActivity): MainView

        @ActivityScoped
        @Binds
        fun bindMainPresenter(presenter: MainPresenterImpl): MainPresenter

        @ActivityScoped
        @Binds
        fun bindMainNavigator(navigator: MainNavigatorImpl): MainNavigator
    }
}
