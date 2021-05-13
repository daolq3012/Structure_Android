package com.ccc.nameapp.extension

import android.app.Activity
import android.content.Context.INPUT_METHOD_SERVICE
import android.content.Intent
import android.graphics.Rect
import android.net.Uri
import android.os.Build
import android.view.View
import android.view.ViewTreeObserver
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.annotation.IdRes
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.ccc.nameapp.utils.Constant
import com.ccc.nameapp.utils.DimensionUtils

fun AppCompatActivity.startActivity(
    @NonNull intent: Intent,
    flags: Int? = null
) {
    flags?.let { intent.flags = it }
    startActivity(intent)
}

fun AppCompatActivity.startActivityForResult(
    @NonNull intent: Intent,
    requestCode: Int,
    flags: Int? = null
) {
    flags?.let { intent.flags = it }
    startActivityForResult(intent, requestCode)
}

fun AppCompatActivity.replaceFragment(
    @IdRes containerId: Int,
    fragment: Fragment,
    addToBackStack: Boolean = false,
    tag: String = fragment::class.java.simpleName
) {
    val transaction = supportFragmentManager.beginTransaction()
    if (addToBackStack) {
        transaction.addToBackStack(tag)
    }
    transaction.replace(containerId, fragment, tag)
    transaction.commitAllowingStateLoss()
    supportFragmentManager.executePendingTransactions()
}

fun AppCompatActivity.goBackFragment(): Boolean {
    val isShowPreviousPage = supportFragmentManager.backStackEntryCount > 0
    if (isShowPreviousPage) {
        supportFragmentManager.popBackStackImmediate()
    }
    return isShowPreviousPage
}

fun AppCompatActivity.openUrl(url: String) {
    if (url.isBlank()) {
        return
    }
    val intent = Intent(Intent.ACTION_VIEW).setData(Uri.parse(url))
    startActivity(intent)
}

fun Activity.hideSoftKeyBoard() {
    window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
}

fun Activity.hideSoftKeyboardFromWindow() {
    if (currentFocus != null) {
        val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }
}

fun Activity.hideSoftKeyboardFromWindow(view: View) {
    val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Activity.showSoftKeyboard() {
    val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
}

fun Activity.getRootView(): View {
    return window.decorView.rootView
}

fun Activity.isKeyboardOpened(): Boolean {
    val visibleBounds = Rect()
    getRootView().getWindowVisibleDisplayFrame(visibleBounds)
    val screenHeight = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        DimensionUtils.calculateScreenHeightForLollipop(applicationContext)
    } else {
        getRootView().height
    }
    var heightDifference = screenHeight - (visibleBounds.bottom - visibleBounds.top)
    val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
    if (resourceId > 0) {
        heightDifference -= resources.getDimensionPixelSize(resourceId)
    }
    return heightDifference > Constant.Keyboard.MIN_HEIGHT_VISIBLE
}

fun Activity.observerKeyboardShow(
    onKeyboardShow: (
        Boolean,
        ViewTreeObserver.OnGlobalLayoutListener
    ) -> Unit
) {
    getRootView().viewTreeObserver.addOnGlobalLayoutListener(object :
        ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            onKeyboardShow(isKeyboardOpened(), this)
        }
    })
}
