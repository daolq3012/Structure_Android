package com.fstyle.structure_android.screen.main

import com.fstyle.structure_android.data.model.User
import com.fstyle.structure_android.data.source.remote.api.error.BaseException
import com.fstyle.structure_android.screen.BasePresenter
import com.fstyle.structure_android.screen.BaseViewModel
import com.fstyle.structure_android.utils.rx.BaseSchedulerProvider

/**
 * Created by le.quang.dao on 10/03/2017.
 */

interface MainContract {

  /**
   * ViewModel
   */
  interface ViewModel : BaseViewModel {
    fun onRequestServerError(e: BaseException)

    fun onSearchUsersSuccess(users: List<User>)

    fun onInvalidKeyWord(errorMsg: String?)

    fun onInvalidLimitNumber(errorMsg: String?)

    fun onShowProgressBar()

    fun onHideProgressBar()
  }

  /**
   * Presenter
   */
  interface Presenter : BasePresenter<ViewModel> {

    fun setSchedulerProvider(schedulerProvider: BaseSchedulerProvider)

    fun validateKeywordInput(keyword: String?)

    fun validateLimitNumberInput(limit: String?)

    fun validateDataInput(keyword: String?, limit: String?): Boolean

    fun searchUsers(keyWord: String?, limit: Int)
  }
}
