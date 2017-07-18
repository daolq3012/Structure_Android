package com.fstyle.structure_android.screen.userdetail;

import android.databinding.BaseObservable
import android.databinding.Bindable
import android.os.Bundle
import com.fstyle.library.MaterialDialog
import com.fstyle.structure_android.BR
import com.fstyle.structure_android.data.model.User
import com.fstyle.structure_android.data.source.remote.api.error.BaseException
import com.fstyle.structure_android.utils.Constant
import com.fstyle.structure_android.widget.dialog.DialogManager


/**
 * Exposes the data to be used in the UserDetail screen.
 */

class UserDetailViewModel(
    private val presenter: UserDetailContract.Presenter,
    bundle: Bundle,
    private val dialogManager: DialogManager) : BaseObservable(), UserDetailContract.ViewModel {

  @get:Bindable
  var user: User? = User()

  init {
    presenter.setViewModel(this)
    val userLogin = bundle.getString(Constant.ARGUMENT_USER_LOGIN)
    presenter.getUserDetailFromServer(userLogin)
  }

  override fun onStart() {
    presenter.onStart()
  }

  override fun onStop() {
    presenter.onStop()
  }

  override fun onGetUserDetailSuccess(user: User?) {
    this.user = user
    notifyPropertyChanged(BR.user)
  }

  override fun onGetUserDetailError(baseException: BaseException) {
    dialogManager.dialogError(baseException.getMessageError(), MaterialDialog.SingleButtonCallback(
        { materialDialog, dialogAction -> materialDialog.dismiss() }))
  }

  override fun onShowProgressBar() {
    dialogManager.showIndeterminateProgressDialog()
  }

  override fun onHideProgressBar() {
    dialogManager.dismissProgressDialog()
  }
}
