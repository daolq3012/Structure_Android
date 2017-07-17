package com.fstyle.structure_android.screen

import android.content.Context
import android.support.v7.widget.RecyclerView

/**
 * Created by le.quang.dao on 14/03/2017.
 * Base Adapter.

 * @param <V> is a type extend from [RecyclerView.ViewHolder]
</V> */

abstract class BaseRecyclerViewAdapter<V : RecyclerView.ViewHolder> protected constructor(
    protected val context: Context) : RecyclerView.Adapter<V>() {

  /**
   * OnRecyclerViewItemClickListener

   * @param <T> Data from item click
  </T> */
  interface OnRecyclerViewItemClickListener<T> {
    fun onItemRecyclerViewClick(item: T)
  }
}
