package com.fstyle.structure_android.utils.binding

import android.databinding.BindingAdapter
import android.support.design.widget.TextInputLayout
import android.support.v7.widget.RecyclerView

/**
 * Created by le.quang.dao on 20/03/2017.
 */

object BindingUtils {

  /**
   * setErrorMessage for TextInputLayout
   */
  @JvmStatic
  @BindingAdapter("errorText")
  fun setErrorMessage(view: TextInputLayout, errorMessage: String?) {
    view.error = errorMessage
  }

  /**
   * setAdapter For RecyclerView
   */
  @JvmStatic
  @BindingAdapter("recyclerAdapter")
  fun setAdapterForRecyclerView(recyclerView: RecyclerView,
      adapter: RecyclerView.Adapter<*>) {
    recyclerView.adapter = adapter
  }
}
