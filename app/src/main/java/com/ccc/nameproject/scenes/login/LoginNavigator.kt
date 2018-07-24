package com.ccc.nameproject.scenes.login

import android.support.v7.app.AppCompatActivity
import com.ccc.nameproject.scenes.BaseNavigator
import com.ccc.nameproject.scenes.main.MainActivity

/**
 * Created by daolq on 5/16/18.
 * nameproject_Android
 */

interface LoginNavigator {
  fun gotoMainScreen()
}

class LoginNavigatorImpl(activity: AppCompatActivity) : BaseNavigator(activity), LoginNavigator {
  override fun gotoMainScreen() {
    activity.startActivity(MainActivity.getInstance(activity))
  }
}
