package com.fstyle.structure_android.screen.searchresult

import android.databinding.BaseObservable
import android.view.View
import com.fstyle.structure_android.data.model.User

/**
 * Created by le.quang.dao on 20/03/2017.
 */

class ItemSearchResultViewModel(private val user: User,
    private val mItemClickListener: ItemUserClickListener?) : BaseObservable() {

  val userLogin: String?
    get() = user.login

  fun onItemClicked(view: View) {
    mItemClickListener?.onItemUserClick(user)
  }
}
