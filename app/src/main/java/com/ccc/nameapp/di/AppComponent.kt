package com.ccc.nameapp.di

import android.app.Application
import com.ccc.nameapp.MainApplication
import com.ccc.nameapp.data.source.remote.RepositoryModule
import com.ccc.nameapp.data.source.remote.service.NetworkModule
import com.ccc.nameapp.di.scopes.AppScoped
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@AppScoped
@Component(
    modules = [AppModule::class, NetworkModule::class, RepositoryModule::class,
        ActivityBindingModule::class, FragmentBindingModule::class, AndroidSupportInjectionModule::class]
)
interface AppComponent : AndroidInjector<MainApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}
