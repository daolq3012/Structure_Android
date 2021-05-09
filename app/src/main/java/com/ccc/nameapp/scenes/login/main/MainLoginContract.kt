package com.ccc.nameapp.scenes.login.main

import androidx.annotation.StringRes
import com.ccc.nameapp.data.model.User
import com.ccc.nameapp.scenes.BasePresenter

/**
 * This specifies the contract between the view and the presenter.
 */
interface MainLoginView {
    fun loginSuccess()
    fun onGetError(throwable: Throwable)
    fun showDialogProgress(@StringRes stringResId: Int)
    fun dismissDialogProgress()
    fun getFriendsSuccess(users: List<User>)
}

interface MainLoginPresenter : BasePresenter {
    fun login(email: String, password: String)
}
