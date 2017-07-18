package com.fstyle.structure_android.screen.userdetail;

import android.databinding.DataBindingUtil
import android.databinding.ObservableField
import android.os.Bundle
import com.fstyle.library.MaterialDialog
import com.fstyle.structure_android.MainApplication
import com.fstyle.structure_android.R
import com.fstyle.structure_android.data.model.User
import com.fstyle.structure_android.data.source.remote.api.error.BaseException
import com.fstyle.structure_android.databinding.ActivityUserDetailBinding
import com.fstyle.structure_android.screen.BaseActivity
import com.fstyle.structure_android.utils.Constant
import com.fstyle.structure_android.widget.dialog.DialogManager
import javax.inject.Inject

/**
 * UserDetail Screen.
 */
class UserDetailActivity : BaseActivity(), UserDetailContract.ViewModel {

  @Inject
  internal lateinit var presenter: UserDetailContract.Presenter
  @Inject
  internal lateinit var dialogManager: DialogManager

  var user: ObservableField<User> = ObservableField(User())

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    DaggerUserDetailComponent.builder()
        .appComponent((application as MainApplication).appComponent)
        .userDetailModule(UserDetailModule(this))
        .build()
        .inject(this)

    val binding = DataBindingUtil.setContentView<ActivityUserDetailBinding>(this,
        R.layout.activity_user_detail)
    binding.viewModel = this

    val userLogin = intent.extras.getString(Constant.ARGUMENT_USER_LOGIN)
    presenter.getUserDetailFromServer(userLogin)
  }

  override fun onStart() {
    super.onStart()
    presenter.onStart()
  }

  override fun onStop() {
    presenter.onStop()
    super.onStop()
  }

  override fun onGetUserDetailSuccess(user: User?) {
    this.user.set(user)
  }

  override fun onRequestServerError(baseException: BaseException) {
    dialogManager.dialogError(baseException.getMessageError())
  }

  override fun onShowProgressBar() {
    dialogManager.showIndeterminateProgressDialog()
  }

  override fun onHideProgressBar() {
    dialogManager.dismissProgressDialog()
  }
}
