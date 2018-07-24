package com.ccc.nameproject.scenes.login

import com.ccc.nameproject.data.source.remote.api.error.BaseException
import com.ccc.nameproject.scenes.BasePresenter
import com.ccc.nameproject.utils.rx.BaseSchedulerProvider

/**
 * Created by daolq on 5/16/18.
 * nameproject_Android
 */

interface LoginView {
  fun onInputDataValid(errorMessageValidate: ErrorMessageValidate)

  fun onLoginSuccess()

  fun onLoginFail(exception: BaseException?)
}

interface LoginPresenter: BasePresenter {
  fun setBaseScheduler(baseSchedulerProvider: BaseSchedulerProvider)

  fun login(email: String, password: String)

  fun loginByFaceBook()
}
