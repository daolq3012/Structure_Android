package com.ccc.nameapp.scenes.login

import javax.inject.Inject

/**
 * Listens to user actions from the UI ({@link LoginActivity}), retrieves the data and updates
 * the UI as required.
 */

class LoginPresenterImpl @Inject constructor(private var view: LoginView?) : LoginPresenter {

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
