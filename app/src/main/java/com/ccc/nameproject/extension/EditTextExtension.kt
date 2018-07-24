package com.ccc.nameproject.extension

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

/**
 * Created by daolq on 5/22/18.
 * nameproject_Android
 */

fun EditText.onChange(f: (String) -> Unit) {
  this.addTextChangedListener(object : TextWatcher {
    override fun afterTextChanged(s: Editable?) {
      f(s.toString())
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
  })
}

fun EditText.moveCursorToEnd() {
  setSelection(text.length)
}
