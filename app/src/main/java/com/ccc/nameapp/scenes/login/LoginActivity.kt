package com.ccc.nameapp.scenes.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.ccc.nameapp.R
import com.ccc.nameapp.scenes.BaseActivity
import com.ccc.nameapp.scenes.login.main.MainLoginFragment
import javax.inject.Inject

/**
 * Login Scene
 */
class LoginActivity : BaseActivity(), LoginView {

    @Inject
    internal lateinit var mPresenter: LoginPresenter
    @Inject
    internal lateinit var mNavigator: LoginNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setMainContentView(R.layout.activity_login)

        hideToolbar()
        showMainLogin()
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

    private fun showMainLogin() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(
            R.id.contentLayout,
            MainLoginFragment.newInstance(), TAG
        ).commitAllowingStateLoss()
    }

    companion object {
        var TAG: String = this::class.java.simpleName

        fun getInstance(context: Context): Intent {
            return Intent(context, LoginActivity::class.java)
        }
    }
}
