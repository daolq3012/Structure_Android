package com.ccc.nameapp.scenes.main

import com.ccc.nameapp.scenes.BaseNavigator
import javax.inject.Inject

interface MainNavigator {
    fun finishActivity()
}

class MainNavigatorImpl @Inject constructor(activity: MainActivity) : BaseNavigator(activity),
    MainNavigator {
    override fun finishActivity() {
        activity.finish()
    }
}
