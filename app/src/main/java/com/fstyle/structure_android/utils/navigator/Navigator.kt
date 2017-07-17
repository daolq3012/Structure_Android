package com.fstyle.structure_android.utils.navigator

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.text.TextUtils
import android.util.Patterns
import com.fstyle.structure_android.R

/**
 * Created by le.quang.dao on 17/03/2017.
 */

class Navigator {

  private var mActivity: Activity
  private var mFragment: Fragment? = null

  constructor(activity: Activity) {
    mActivity = activity
  }

  constructor(fragment: Fragment) {
    mFragment = fragment
    mActivity = fragment.activity
  }

  private fun startActivity(intent: Intent) {
    mActivity.startActivity(intent)
    setActivityTransactionAnimation(ActivityTransition.START)
  }

  fun startActivity(clazz: Class<out Activity>) {
    val intent = Intent(mActivity, clazz)
    startActivity(intent)
  }

  fun startActivity(clazz: Class<out Activity>, args: Bundle) {
    val intent = Intent(mActivity, clazz)
    intent.putExtras(args)
    startActivity(intent)
  }

  fun startActivityAtRoot(clazz: Class<out Activity>) {
    val intent = Intent(mActivity, clazz)
    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
    startActivity(intent)
  }

  fun startActivityForResult(intent: Intent, requestCode: Int) {
    mActivity.startActivityForResult(intent, requestCode)
    setActivityTransactionAnimation(ActivityTransition.START)
  }

  fun startActivityForResult(clazz: Class<out Activity>, args: Bundle,
      requestCode: Int) {
    val intent = Intent(mActivity, clazz)
    intent.putExtras(args)
    startActivityForResult(intent, requestCode)
  }

  fun finishActivity() {
    mActivity.finish()
    setActivityTransactionAnimation(ActivityTransition.FINISH)
  }

  fun finishActivityWithResult(intent: Intent, resultCode: Int) {
    mActivity.setResult(resultCode, intent)
    finishActivity()
  }

  fun openUrl(url: String) {
    if (TextUtils.isEmpty(url) || !Patterns.WEB_URL.matcher(url).matches()) {
      return
    }
    val intent = Intent(Intent.ACTION_VIEW).setData(Uri.parse(url))
    startActivity(intent)
  }

  // Fragment

  /**
   * Go to next fragment which nested inside current fragment

   * @param fragment new child fragment
   */
  fun goNextChildFragment(@IdRes containerViewId: Int, fragment: Fragment,
      addToBackStack: Boolean, animation: Int, tag: String) {
    val transaction = mFragment?.childFragmentManager?.beginTransaction()
    setFragmentTransactionAnimation(transaction, animation)
    if (addToBackStack) {
      transaction?.addToBackStack(fragment.javaClass.simpleName)
    }
    transaction?.replace(containerViewId, fragment, tag)
    transaction?.commitAllowingStateLoss()
    mFragment?.childFragmentManager?.executePendingTransactions()
  }

  /**
   * Always keep 1 fragment in container

   * @return true if fragment stack has pop
   */
  fun goBackChildFragment(): Boolean {
    val isShowPrevious = mFragment?.childFragmentManager?.backStackEntryCount!! > 1
    if (isShowPrevious) {
      mFragment?.childFragmentManager?.popBackStackImmediate()
    }
    return isShowPrevious
  }

  private fun setFragmentTransactionAnimation(transaction: FragmentTransaction?, animation: Int) {
    when (animation) {
      NavigateAnim.FADED -> transaction?.setCustomAnimations(android.R.anim.fade_in,
          android.R.anim.fade_out,
          android.R.anim.fade_in, android.R.anim.fade_out)
      NavigateAnim.RIGHT_LEFT -> transaction?.setCustomAnimations(R.anim.slide_right_in,
          R.anim.slide_left_out,
          R.anim.slide_left_in, R.anim.slide_right_out)
      NavigateAnim.LEFT_RIGHT -> transaction?.setCustomAnimations(R.anim.slide_left_in,
          R.anim.slide_right_out,
          R.anim.slide_right_in, R.anim.slide_left_out)
      NavigateAnim.BOTTOM_UP -> transaction?.setCustomAnimations(R.anim.slide_bottom_in,
          R.anim.slide_top_out,
          R.anim.slide_top_in, R.anim.slide_bottom_out)
      NavigateAnim.NONE -> {
      }
      else -> {
      }
    }
  }

  private fun setActivityTransactionAnimation(animation: Int) {
    when (animation) {
      ActivityTransition.START -> mActivity.overridePendingTransition(R.anim.translate_left,
          R.anim.translate_still)
      ActivityTransition.FINISH -> mActivity.overridePendingTransition(R.anim.translate_still,
          R.anim.translate_right)
      ActivityTransition.NONE -> {
      }
      else -> {
      }
    }
  }
}
