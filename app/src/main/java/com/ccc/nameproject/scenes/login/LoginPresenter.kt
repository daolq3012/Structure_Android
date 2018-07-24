package com.ccc.nameproject.scenes.login

import com.ccc.nameproject.data.source.remote.api.error.BaseException
import com.ccc.nameproject.extension.notNull
import com.ccc.nameproject.repositories.TokenRepository
import com.ccc.nameproject.repositories.UserRepository
import com.ccc.nameproject.utils.rx.BaseSchedulerProvider
import com.ccc.nameproject.utils.validator.Validator
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by daolq on 5/16/18.
 * nameproject_Android
 */
class LoginPresenterImpl(private val view: LoginView,
    private val validator: Validator,
    private val userRepository: UserRepository,
    private val tokenRepository: TokenRepository) : LoginPresenter {

  private lateinit var baseScheduler: BaseSchedulerProvider
  private val mCompositeDisposable = CompositeDisposable()

  override fun onStart() {

  }

  override fun onStop() {
    mCompositeDisposable.clear()
  }

  override fun setBaseScheduler(baseSchedulerProvider: BaseSchedulerProvider) {
    this.baseScheduler = baseSchedulerProvider
  }

  override fun login(email: String, password: String) {
    val inputEmailError = validator.validateEmail(email)
    val inputPasswordError = validator.validatePassword(password)
    view.onInputDataValid(
        ErrorMessageValidate(emailError = inputEmailError, passwordError = inputPasswordError))
    if (inputEmailError != null || inputPasswordError != null) {
      return
    }
    val disposable = userRepository.login(email, password)
        .subscribeOn(baseScheduler.io())
        .observeOn(baseScheduler.ui()).subscribe(
            { token ->
              token.notNull {
                tokenRepository.saveToken(it)
                view.onLoginSuccess()
              }
            },
            { error -> view.onLoginFail(error as? BaseException) })
    mCompositeDisposable.add(disposable)
  }

  override fun loginByFaceBook() {
    TODO(
        "not implemented") //To change body of created functions use File | Settings | File Templates.
  }
}

data class ErrorMessageValidate(val emailError: String?, val passwordError: String?)
