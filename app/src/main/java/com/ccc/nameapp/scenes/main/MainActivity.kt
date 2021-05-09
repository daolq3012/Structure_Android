package com.ccc.nameapp.scenes.main

import android.os.Bundle
import com.ccc.nameapp.R
import com.ccc.nameapp.scenes.BaseActivity
import javax.inject.Inject

class MainActivity : BaseActivity(), MainView {

    @Inject
    internal lateinit var mPresenter: MainPresenter
    @Inject
    internal lateinit var mNavigator: MainNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setMainContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        mPresenter.onStart()
    }

    override fun onStop() {
        mPresenter.onStop()
        super.onStop()
    }

    override fun onDestroy() {
        mPresenter.onDestroy()
        super.onDestroy()
    }
}
