package com.fstyle.structure_android.screen.searchresult

import android.databinding.BaseObservable
import android.util.Log
import com.fstyle.structure_android.data.model.User

/**
 * Created by le.quang.dao on 21/03/2017.
 */

class SearchResultViewModel(private val mPresenter: SearchResultContract.Presenter,
    val adapter: SearchResultAdapter) : BaseObservable(), SearchResultContract.ViewModel, ItemUserClickListener {

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
    Log.d("onItemUserClick", user.toString())
  }
}
