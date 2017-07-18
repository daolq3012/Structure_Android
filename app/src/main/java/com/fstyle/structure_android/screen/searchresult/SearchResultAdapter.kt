package com.fstyle.structure_android.screen.searchresult

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.fstyle.structure_android.R
import com.fstyle.structure_android.data.model.User
import com.fstyle.structure_android.databinding.ItemSearchResultBinding
import com.fstyle.structure_android.screen.BaseRecyclerViewAdapter
import java.util.*

/**
 * Created by le.quang.dao on 14/03/2017.
 */

class SearchResultAdapter constructor(context: Context,
    users: List<User>?) : BaseRecyclerViewAdapter<SearchResultAdapter.ItemViewHolder>(context) {

  private val mUsers: MutableList<User> = ArrayList<User>()
  private val mItemUserClickListener: ItemUserClickListener

  init {
    if (users != null)
      mUsers.addAll(users)
    if (context !is ItemUserClickListener) {
      throw RuntimeException("Activity use this Adapter must implement ItemUserClickListener")
    }
    mItemUserClickListener = context
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
    val binding = DataBindingUtil.inflate<ItemSearchResultBinding>(
        LayoutInflater.from(parent.context),
        R.layout.item_search_result, parent, false)
    return ItemViewHolder(binding, mItemUserClickListener)
  }

  override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
    holder.bind(mUsers[position])
  }

  override fun getItemCount(): Int {
    return mUsers.size
  }

  /**
   * ItemViewHolder
   */
  class ItemViewHolder(private val mBinding: ItemSearchResultBinding,
      private val mItemClickListener: ItemUserClickListener?) : RecyclerView.ViewHolder(
      mBinding.root) {

    fun bind(user: User) {
      mBinding.viewModel = ItemSearchResultViewModel(user, mItemClickListener)
      mBinding.executePendingBindings()
    }
  }
}
