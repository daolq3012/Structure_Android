package com.fstyle.structure_android.screen.main

import android.app.Activity
import android.content.Context
import com.fstyle.structure_android.data.source.UserRepository
import com.fstyle.structure_android.data.source.local.sqlite.UserLocalDataSource
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
    return Validator(context, MainViewModel::class.java)
  }

  @ActivityScope
  @Provides
  fun provideViewModel(presenter: MainContract.Presenter,
      dialogManager: DialogManager, navigator: Navigator): MainContract.ViewModel {
    return MainViewModel(presenter, dialogManager, navigator)
  }

  @ActivityScope
  @Provides
  fun providePresenter(userRepository: UserRepository,
      validator: Validator, schedulerProvider: BaseSchedulerProvider): MainContract.Presenter {
    return MainPresenter(userRepository, validator, schedulerProvider)
  }

  @ActivityScope
  @Provides
  fun provideUserRepository(localDataSource: UserLocalDataSource,
      remoteDataSource: UserRemoteDataSource): UserRepository {
    return UserRepository(localDataSource, remoteDataSource)
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
