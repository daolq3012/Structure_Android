package com.fstyle.structure_android.screen.userdetail;

import com.fstyle.structure_android.data.model.User
import com.fstyle.structure_android.data.source.remote.api.error.BaseException
import com.fstyle.structure_android.screen.BasePresenter;
import com.fstyle.structure_android.screen.BaseViewModel;
import com.fstyle.structure_android.utils.rx.BaseSchedulerProvider

/**
 * This specifies the contract between the view and the presenter.
 */
interface UserDetailContract {
  /**
   * View.
   */
  interface ViewModel : BaseViewModel {
    fun onGetUserDetailSuccess(user: User?)
    fun onRequestServerError(baseException: BaseException)
    fun onShowProgressBar()
    fun onHideProgressBar()
  }

  /**
   * Presenter.
   */
  interface Presenter : BasePresenter<ViewModel> {
    fun setSchedulerProvider(schedulerProvider: BaseSchedulerProvider?)

    fun getUserDetailFromServer(userLogin: String?)
  }
}
