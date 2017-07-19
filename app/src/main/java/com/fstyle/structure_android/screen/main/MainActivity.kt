package com.fstyle.structure_android.screen.main

import android.databinding.DataBindingUtil
import android.databinding.ObservableField
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import com.fstyle.structure_android.MainApplication
import com.fstyle.structure_android.R
import com.fstyle.structure_android.data.model.User
import com.fstyle.structure_android.data.source.remote.api.error.BaseException
import com.fstyle.structure_android.databinding.ActivityMainBinding
import com.fstyle.structure_android.screen.BaseActivity
import com.fstyle.structure_android.screen.searchresult.SearchResultActivity
import com.fstyle.structure_android.utils.Constant
import com.fstyle.structure_android.utils.common.StringUtils
import com.fstyle.structure_android.utils.navigator.Navigator
import com.fstyle.structure_android.utils.validator.Rule
import com.fstyle.structure_android.utils.validator.ValidType
import com.fstyle.structure_android.utils.validator.Validation
import com.fstyle.structure_android.widget.dialog.DialogManager
import java.util.*
import javax.inject.Inject

/**
 * Create by DaoLQ
 */
class MainActivity : BaseActivity(), MainContract.ViewModel {

  @Inject
  lateinit var presenter: MainContract.Presenter
  @Inject
  lateinit var mDialogManager: DialogManager
  @Inject
  lateinit var mNavigator: Navigator

  @Validation(Rule(types = intArrayOf(ValidType.NG_WORD, ValidType.NON_EMPTY),
      message = R.string.error_unusable_characters))
  var keyWord: ObservableField<String> = ObservableField()
  @Validation(Rule(types = intArrayOf(ValidType.VALUE_RANGE_0_100), message = R.string
      .error_lenght_from_0_to_100),
      Rule(types = intArrayOf(ValidType.NON_EMPTY), message = R.string.must_not_empty))
  var limit: ObservableField<String> = ObservableField()
  var keywordErrorMsg: ObservableField<String> = ObservableField()
  var limitErrorMsg: ObservableField<String> = ObservableField()

  override fun onCreate(savedInstanceState: Bundle?) {
    DaggerMainComponent.builder()
        .appComponent((application as MainApplication).appComponent)
        .mainModule(MainModule(this))
        .build()
        .inject(this)
    super.onCreate(savedInstanceState)

    val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
    binding.viewModel = this
  }

  override fun onStart() {
    super.onStart()
    presenter.onStart()
  }

  override fun onStop() {
    presenter.onStop()
    super.onStop()
  }

  override fun onRequestServerError(e: BaseException) {
    mDialogManager.dialogError(e.getMessageError())
  }

  override fun onSearchUsersSuccess(users: List<User>) {
    val bundle = Bundle()
    bundle.putParcelableArrayList(Constant.ARGUMENT_LIST_USER, users as ArrayList<out Parcelable>)
    mNavigator.startActivity(SearchResultActivity::class.java, bundle)
  }

  override fun onInvalidKeyWord(errorMsg: String?) {
    keywordErrorMsg.set(errorMsg)
  }

  override fun onInvalidLimitNumber(errorMsg: String?) {
    limitErrorMsg.set(errorMsg)
  }

  override fun onShowProgressBar() {
    sIdlingNotificationListener?.increment()
    mDialogManager.showIndeterminateProgressDialog()
  }

  override fun onHideProgressBar() {
    sIdlingNotificationListener?.decrement()
    mDialogManager.dismissProgressDialog()
  }

  fun onSearchButtonClicked(view: View?) {
    if (!presenter.validateDataInput(keyWord.get(), limit.get())) {
      return
    }
    presenter.searchUsers(keyWord.get(), StringUtils.convertStringToNumber(limit.get()))
  }
}
