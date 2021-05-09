package com.ccc.nameapp.scenes

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.annotation.MenuRes
import com.ccc.nameapp.R
import com.ccc.nameapp.extension.gone
import com.ccc.nameapp.widgets.view.ToolbarView
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.layout_base.*

abstract class BaseActivity : DaggerAppCompatActivity(), ToolbarView.OnBackClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_base)
        toolbarView.setOnBackClickListener(this)
    }

    override fun onBackItemClick() {
        finish()
    }

    fun setMainContentView(@LayoutRes layoutId: Int) {
        val contentView = LayoutInflater.from(applicationContext).inflate(layoutId, null)
        content_layout.addView(contentView)
    }

    fun logError(tag: String, message: String, throwable: Throwable) {
        Log.e(tag, message, throwable)
    }

    fun toast(message: String, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(applicationContext, message, duration).show()
    }

    fun inflateToolbarPopupMenu(@MenuRes menuResId: Int) {
        toolbarView.inflateToolbarPopupMenu(this, menuResId)
    }

    fun hideToolbar() {
        toolbarView.gone()
    }
}
