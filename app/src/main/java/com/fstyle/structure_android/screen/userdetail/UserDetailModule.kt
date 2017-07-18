package com.fstyle.structure_android.screen.userdetail;

import android.app.Activity
import com.fstyle.structure_android.data.source.UserRepository
import com.fstyle.structure_android.data.source.UserRepositoryImpl
import com.fstyle.structure_android.data.source.remote.UserRemoteDataSource
import com.fstyle.structure_android.utils.dagger.ActivityScope
import com.fstyle.structure_android.utils.rx.BaseSchedulerProvider
import com.fstyle.structure_android.widget.dialog.DialogManager
import com.fstyle.structure_android.widget.dialog.DialogManagerImpl
import dagger.Module
import dagger.Provides

/**
 * This is a Dagger module. We use this to pass in the View dependency to
 * the {@link UserDetailPresenter}.
 */
@Module
class UserDetailModule(private val mActivity: Activity) {

  @ActivityScope
  @Provides
  fun providePresenter(userRepository: UserRepository,
      baseSchedulerProvider: BaseSchedulerProvider): UserDetailContract.Presenter {
    val presenter = UserDetailPresenter(userRepository)
    presenter.setSchedulerProvider(baseSchedulerProvider)
    presenter.setViewModel(mActivity as UserDetailContract.ViewModel)
    return presenter
  }

  @ActivityScope
  @Provides
  fun provideDialogManager(): DialogManager {
    return DialogManagerImpl(mActivity)
  }

  @ActivityScope
  @Provides
  fun provideUserRepository(userRemoteDataSource: UserRemoteDataSource): UserRepository {
    return UserRepositoryImpl(userRemoteDataSource)
  }
}
