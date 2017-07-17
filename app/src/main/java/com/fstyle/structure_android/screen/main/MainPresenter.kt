package com.fstyle.structure_android.screen.main

import android.util.Log
import com.fstyle.structure_android.data.model.User
import com.fstyle.structure_android.data.source.UserRepository
import com.fstyle.structure_android.data.source.remote.api.error.BaseException
import com.fstyle.structure_android.data.source.remote.api.error.RequestError
import com.fstyle.structure_android.screen.BaseViewModel
import com.fstyle.structure_android.utils.common.StringUtils
import com.fstyle.structure_android.utils.rx.BaseSchedulerProvider
import com.fstyle.structure_android.utils.validator.Validator
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer

/**
 * Created by le.quang.dao on 10/03/2017.
 */

internal class MainPresenter(private val mUserRepository: UserRepository,
    private val mValidator: Validator,
    private var mSchedulerProvider: BaseSchedulerProvider?) : MainContract.Presenter {

  private var mMainViewModel: MainContract.ViewModel? = null
  private val mCompositeSubscription: CompositeDisposable = CompositeDisposable()

  init {
    val subscription = mValidator.initNGWordPattern()
    mCompositeSubscription.add(subscription)
  }

  override fun onStart() {}

  override fun setSchedulerProvider(schedulerProvider: BaseSchedulerProvider) {
    mSchedulerProvider = schedulerProvider
  }

  override fun onStop() {
    mCompositeSubscription.clear()
  }

  override fun setViewModel(viewModel: MainContract.ViewModel) {
    mMainViewModel = viewModel
  }

  override fun validateKeywordInput(keyword: String?) {
    var message: String? = mValidator.validateValueNonEmpty(keyword)
    if (StringUtils.isBlank(message)) {
      message = mValidator.validateNGWord(keyword)
    }
    mMainViewModel!!.onInvalidKeyWord(message)
  }

  override fun validateLimitNumberInput(limit: String?) {
    var message: String? = mValidator.validateValueNonEmpty(limit)
    if (StringUtils.isBlank(message)) {
      message = mValidator.validateValueRangeFrom0to100(limit)
    }
    mMainViewModel!!.onInvalidLimitNumber(message)
  }

  override fun validateDataInput(keyword: String?, limit: String?): Boolean {
    validateKeywordInput(keyword)
    validateLimitNumberInput(limit)
    try {
      return mValidator.validateAll<BaseViewModel>(mMainViewModel!!)
    } catch (e: IllegalAccessException) {
      Log.e(TAG, "validateDataInput: ", e)
      return false
    }

  }

  override fun searchUsers(keyWord: String?, limit: Int) {
    val subscription = mUserRepository.searchUsers(keyWord!!, limit)
        .subscribeOn(mSchedulerProvider!!.io())
        .observeOn(mSchedulerProvider!!.ui())
        .subscribe(Consumer<List<User>> { users -> mMainViewModel!!.onSearchUsersSuccess(users) },
            object : RequestError() {
              override fun onRequestError(error: BaseException) {
                mMainViewModel!!.onSearchError(error)
              }
            })
    mCompositeSubscription.add(subscription)
  }

  companion object {
    private val TAG = MainPresenter::class.java.name
  }
}
