package com.fstyle.structure_android.screen.main

import android.app.Activity
import android.content.Context
import com.fstyle.structure_android.data.source.UserRepository
import com.fstyle.structure_android.data.source.UserRepositoryImpl
import com.fstyle.structure_android.data.source.remote.UserRemoteDataSource
import com.fstyle.structure_android.utils.dagger.ActivityScope
import com.fstyle.structure_android.utils.navigator.Navigator
import com.fstyle.structure_android.utils.rx.BaseSchedulerProvider
import com.fstyle.structure_android.utils.validator.Validator
import com.fstyle.structure_android.widget.dialog.DialogManager
import com.fstyle.structure_android.widget.dialog.DialogManagerImpl
import dagger.Module
import dagger.Provides

/**
 * Created by le.quang.dao on 21/03/2017.
 */

@Module
class MainModule(private val mActivity: Activity) {

  @ActivityScope
  @Provides
  fun provideValidator(context: Context): Validator {
    return Validator(context, MainActivity::class.java)
  }

  @ActivityScope
  @Provides
  fun providePresenter(userRepository: UserRepository,
      validator: Validator, schedulerProvider: BaseSchedulerProvider): MainContract.Presenter {
    val presenter = MainPresenter(userRepository, validator)
    presenter.setViewModel(mActivity as MainContract.ViewModel)
    presenter.setSchedulerProvider(schedulerProvider)
    return presenter
  }

  @ActivityScope
  @Provides
  fun provideUserRepository(remoteDataSource: UserRemoteDataSource): UserRepository {
    return UserRepositoryImpl(remoteDataSource)
  }

  @ActivityScope
  @Provides
  fun provideDialogManager(): DialogManager {
    return DialogManagerImpl(mActivity)
  }

  @ActivityScope
  @Provides
  fun provideNavigator(): Navigator {
    return Navigator(mActivity)
  }
}
