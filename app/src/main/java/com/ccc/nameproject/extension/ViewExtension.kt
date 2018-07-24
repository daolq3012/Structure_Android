package com.ccc.nameproject.extension

import android.view.View

/**
 * Created by daolq on 5/15/18.
 * nameproject_Android
 */

fun View.show() {
  visibility = View.VISIBLE
}

fun View.hide() {
  visibility = View.INVISIBLE
}

fun View.gone() {
  visibility = View.GONE
}

fun View.isVisible(): Boolean {
  return visibility == View.VISIBLE
}
