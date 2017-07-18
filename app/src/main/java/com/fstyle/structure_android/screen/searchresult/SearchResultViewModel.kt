package com.fstyle.structure_android.screen.searchresult

import android.os.Bundle
import com.fstyle.structure_android.data.model.User
import com.fstyle.structure_android.screen.userdetail.UserDetailActivity
import com.fstyle.structure_android.utils.Constant
import com.fstyle.structure_android.utils.navigator.Navigator

/**
 * Created by le.quang.dao on 21/03/2017.
 */

class SearchResultViewModel(
    private val mPresenter: SearchResultContract.Presenter,
    val adapter: SearchResultAdapter,
    private val navigator: Navigator) : SearchResultContract.ViewModel, ItemUserClickListener {

  init {
    mPresenter.setViewModel(this)
    this.adapter.setItemClickListener(this)
  }

  override fun onStart() {
    mPresenter.onStart()
  }

  override fun onStop() {
    mPresenter.onStop()
  }

  override fun onItemUserClick(user: User) {
    val bundle: Bundle = Bundle()
    bundle.putString(Constant.ARGUMENT_USER_LOGIN, user.login)
    navigator.startActivity(UserDetailActivity::class.java, bundle)
  }
}
