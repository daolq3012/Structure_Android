package com.ccc.nameapp.scenes.login

import com.ccc.nameapp.di.scopes.ActivityScoped
import dagger.Binds
import dagger.Module

@Module(includes = [LoginModule.LoginAbstractModule::class])
class LoginModule {
    @Module
    interface LoginAbstractModule {
        @ActivityScoped
        @Binds
        fun bindLoginView(activity: LoginActivity): LoginView

        @ActivityScoped
        @Binds
        fun bindLoginPresenter(presenter: LoginPresenterImpl): LoginPresenter

        @ActivityScoped
        @Binds
        fun bindLoginNavigator(navigator: LoginNavigatorImpl): LoginNavigator
    }
}
