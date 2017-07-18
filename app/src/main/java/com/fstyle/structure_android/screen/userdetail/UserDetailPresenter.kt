package com.fstyle.structure_android.screen.userdetail;

import com.fstyle.structure_android.data.source.UserRepository
import com.fstyle.structure_android.data.source.remote.api.error.BaseException
import com.fstyle.structure_android.utils.rx.BaseSchedulerProvider

/**
 * Listens to user actions from the UI ({@link UserDetailActivity}), retrieves the data and updates
 * the UI as required.
 */
class UserDetailPresenter(
    private val userRepository: UserRepository) : UserDetailContract.Presenter {

  private var schedulerProvider: BaseSchedulerProvider? = null
  private var mViewModel: UserDetailContract.ViewModel? = null

  override fun onStart() {}

  override fun onStop() {}

  override fun setViewModel(viewModel: UserDetailContract.ViewModel) {
    mViewModel = viewModel
  }

  override fun setSchedulerProvider(schedulerProvider: BaseSchedulerProvider?) {
    this.schedulerProvider = schedulerProvider
  }

  override fun getUserDetailFromServer(userLogin: String?) {
    userRepository.getUserDetailFromServer(userLogin)
        .subscribeOn(schedulerProvider?.io())
        .doOnSubscribe { mViewModel?.onShowProgressBar() }
        .observeOn(schedulerProvider?.ui())
        .doAfterTerminate { mViewModel?.onHideProgressBar() }
        .subscribe({ user -> mViewModel?.onGetUserDetailSuccess(user) },
            { error -> mViewModel?.onGetUserDetailError(error as BaseException) })
  }
}
