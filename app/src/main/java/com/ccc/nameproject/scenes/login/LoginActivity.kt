package com.ccc.nameproject.scenes.login

import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.InputType
import android.view.View
import com.ccc.nameproject.MainApplication
import com.ccc.nameproject.R
import com.ccc.nameproject.data.source.remote.api.error.BaseException
import com.ccc.nameproject.extension.*
import com.ccc.nameproject.repositories.TokenRepository
import com.ccc.nameproject.repositories.UserRepository
import com.ccc.nameproject.utils.rx.BaseSchedulerProvider
import com.ccc.nameproject.utils.validator.Validator
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject

class LoginActivity : AppCompatActivity(), LoginView {

  private lateinit var presenter: LoginPresenter
  private lateinit var navigator: LoginNavigator

  @Inject
  internal lateinit var mValidator: Validator
  @Inject
  internal lateinit var mUserRepository: UserRepository
  @Inject
  internal lateinit var mTokenRepository: TokenRepository
  @Inject
  internal lateinit var mBaseSchedulerProvider: BaseSchedulerProvider

  override fun onCreate(savedInstanceState: Bundle?) {
    if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
      setTheme(R.style.AppTheme_NoActionBar_FullScreen)
    }
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_login)

    (application as MainApplication).appComponent.inject(this)

    presenter = LoginPresenterImpl(this, mValidator, mUserRepository,
        mTokenRepository)
    presenter.setBaseScheduler(mBaseSchedulerProvider)
    navigator = LoginNavigatorImpl(this)

    passwordEdittext.onChange {
      if (it.isBlank()) {
        showPasswordImage.gone()
        hidePasswordImage.gone()
        return@onChange
      }
      if (!showPasswordImage.isVisible() && !hidePasswordImage.isVisible()) {
        showPasswordImage.show()
      }
    }
  }

  override fun onStart() {
    super.onStart()
    presenter.onStart()
  }

  override fun onStop() {
    presenter.onStop()
    super.onStop()
  }

  override fun onInputDataValid(errorMessageValidate: ErrorMessageValidate) {
    emailTextInputLayout.error = errorMessageValidate.emailError
    emailTextInputLayout.isErrorEnabled = !errorMessageValidate.emailError.isNullOrBlank()
    passwordTextInputLayout.error = errorMessageValidate.passwordError
    passwordTextInputLayout.isErrorEnabled = !errorMessageValidate.passwordError.isNullOrBlank()
  }

  override fun onLoginSuccess() {
    navigator.gotoMainScreen()
  }

  override fun onLoginFail(exception: BaseException?) {

  }

  fun onClickHidePassword(view: View) {
    showPasswordImage.show()
    hidePasswordImage.gone()
    passwordEdittext.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
    passwordEdittext.moveCursorToEnd()
  }

  fun onClickShowPassword(view: View) {
    showPasswordImage.gone()
    hidePasswordImage.show()
    passwordEdittext.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
    passwordEdittext.moveCursorToEnd()
  }

  fun onClickLogin(view: View) {
    presenter.login(emailEditText.text.toString(),
        passwordEdittext.text.toString())
  }

  fun onClickLoginByFaceBook(view: View) {
    presenter.loginByFaceBook()
  }
}
