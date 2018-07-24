package com.ccc.nameproject.widgets

import android.content.Context
import android.graphics.ColorFilter
import android.support.design.widget.TextInputLayout
import android.support.v4.graphics.drawable.DrawableCompat
import android.util.AttributeSet

/**
 * Created by daolq on 5/21/18.
 * nameproject_Android
 */

class NoChangingBackgroundTextInputLayout : TextInputLayout {

  /**
   * Get the EditText's default background color.
   *
   * @return [ColorFilter]
   */
  private val backgroundDefaultColorFilter: ColorFilter?
    get() {
      var defaultColorFilter: ColorFilter? = null
      if (editText != null && editText!!.background != null)
        defaultColorFilter = DrawableCompat.getColorFilter(editText!!.background)
      return defaultColorFilter
    }

  constructor(context: Context) : super(context)

  constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

  constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs,
      defStyleAttr)

  override fun setError(error: CharSequence?) {
    val defaultColorFilter = backgroundDefaultColorFilter
    super.setError(error)
    //Reset EditText's background color to default.
    updateBackgroundColorFilter(defaultColorFilter)
  }

  override fun drawableStateChanged() {
    val defaultColorFilter = backgroundDefaultColorFilter
    super.drawableStateChanged()
    //Reset EditText's background color to default.
    updateBackgroundColorFilter(defaultColorFilter)
  }

  /**
   * If [.getEditText] is not null & [.getEditText] is not null,
   * update the [ColorFilter] of [.getEditText].
   *
   * @param colorFilter [ColorFilter]
   */
  private fun updateBackgroundColorFilter(colorFilter: ColorFilter?) {
    if (editText != null && editText!!.background != null)
      editText!!.background.colorFilter = colorFilter
  }
}
