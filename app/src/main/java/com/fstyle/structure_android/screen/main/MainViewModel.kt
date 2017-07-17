package com.fstyle.structure_android.screen.main

import android.databinding.BaseObservable
import android.databinding.Bindable
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import com.fstyle.library.MaterialDialog
import com.fstyle.structure_android.BR
import com.fstyle.structure_android.R
import com.fstyle.structure_android.data.model.User
import com.fstyle.structure_android.data.source.remote.api.error.BaseException
import com.fstyle.structure_android.screen.searchresult.SearchResultActivity
import com.fstyle.structure_android.utils.Constant
import com.fstyle.structure_android.utils.common.StringUtils
import com.fstyle.structure_android.utils.navigator.Navigator
import com.fstyle.structure_android.utils.validator.Rule
import com.fstyle.structure_android.utils.validator.ValidType
import com.fstyle.structure_android.utils.validator.Validation
import com.fstyle.structure_android.widget.dialog.DialogManager
import java.util.*

/**
 * Created by Sun on 3/20/2017.
 */

class MainViewModel(private val mPresenter: MainContract.Presenter,
    private val mDialogManager: DialogManager,
    private val mNavigator: Navigator) : BaseObservable(), MainContract.ViewModel {

  @Validation(Rule(types = intArrayOf(ValidType.NG_WORD, ValidType.NON_EMPTY),
      message = R.string.error_unusable_characters))
  var keyWord: String? = null
    set(keyWord) {
      field = keyWord
      mPresenter.validateKeywordInput(keyWord)
    }
  @Validation(Rule(types = intArrayOf(ValidType.VALUE_RANGE_0_100), message = R.string
      .error_lenght_from_0_to_100),
      Rule(types = intArrayOf(ValidType.NON_EMPTY), message = R.string.must_not_empty))
  var limit: String? = null
    set(limit) {
      field = limit
      mPresenter.validateLimitNumberInput(limit)
    }
  @get:Bindable
  var keywordErrorMsg: String? = null
    set(keywordErrorMsg) {
      field = keywordErrorMsg
      notifyPropertyChanged(BR.keywordErrorMsg)
    }
  @get:Bindable
  var limitErrorMsg: String? = null
    set(limitErrorMsg) {
      field = limitErrorMsg
      notifyPropertyChanged(BR.limitErrorMsg)
    }

  init {
    mPresenter.setViewModel(this)
  }

  override fun onStart() {
    mPresenter.onStart()
  }

  override fun onStop() {
    mPresenter.onStop()
  }

  override fun onSearchError(e: BaseException) {
    mDialogManager.dismissProgressDialog()
    mDialogManager.dialogError(e.getMessageError(),
        MaterialDialog.SingleButtonCallback { materialDialog, dialogAction ->
          onSearchButtonClicked(null)
        })
  }

  override fun onSearchUsersSuccess(users: List<User>) {
    mDialogManager.dismissProgressDialog()
    val bundle = Bundle()
    bundle.putParcelableArrayList(Constant.ARGUMENT_LIST_USER, users as ArrayList<out Parcelable>)
    mNavigator.startActivity(SearchResultActivity::class.java, bundle)
  }

  override fun onInvalidKeyWord(errorMsg: String?) {
    keywordErrorMsg = errorMsg
  }

  override fun onInvalidLimitNumber(errorMsg: String?) {
    limitErrorMsg = errorMsg
  }

  fun onSearchButtonClicked(view: View?) {
    if (!mPresenter.validateDataInput(keyWord!!, limit!!)) {
      return
    }
    mDialogManager.showIndeterminateProgressDialog()
    mPresenter.searchUsers(keyWord!!, StringUtils.convertStringToNumber(limit))
  }
}
