package com.fstyle.structure_android.screen

import android.support.v7.app.AppCompatActivity
import com.fstyle.structure_android.utils.test.CountingIdlingResourceListener

/**
 * Created by le.quang.dao on 10/03/2017.
 */

open class BaseActivity : AppCompatActivity() {
  var sIdlingNotificationListener: CountingIdlingResourceListener? = null

  fun setIdlingNotificationListener(idlingNotificationListener: CountingIdlingResourceListener?) {
    sIdlingNotificationListener = idlingNotificationListener
  }
}
