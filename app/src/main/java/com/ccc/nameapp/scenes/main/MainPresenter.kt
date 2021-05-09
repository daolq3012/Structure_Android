package com.ccc.nameapp.scenes.main

import javax.inject.Inject

class MainPresenterImpl @Inject constructor(private var view: MainView?) : MainPresenter {

    override fun onStart() {
        // No-op
    }

    override fun onStop() {
        // No-op
    }

    override fun onDestroy() {
        view = null
    }
}
