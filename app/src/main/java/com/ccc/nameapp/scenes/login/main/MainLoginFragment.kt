package com.ccc.nameapp.scenes.login.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import com.ccc.nameapp.R
import com.ccc.nameapp.data.model.User
import com.ccc.nameapp.scenes.BaseActivity
import com.ccc.nameapp.utils.Validator
import com.ccc.nameapp.widgets.DialogManager
import com.ccc.nameapp.widgets.DialogManagerImpl
import com.ccc.nameapp.widgets.StringUtils
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_main_login.view.*
import javax.inject.Inject

/**
 * MainLogin Screen.
 */
class MainLoginFragment : DaggerFragment(), MainLoginView {

    @Inject
    internal lateinit var mPresenter: MainLoginPresenter

    @Inject
    internal lateinit var mNavigator: MainLoginNavigator

    @Inject
    internal lateinit var mValidator: Validator

    private lateinit var mView: View
    private lateinit var mDialogManager: DialogManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mView = inflater.inflate(R.layout.fragment_main_login, container, false)
        activity?.let {
            mDialogManager = DialogManagerImpl(it)
        }

        handleEvents()
        return mView
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

    override fun loginSuccess() {
        // TODO handle logic after login
    }

    override fun getFriendsSuccess(users: List<User>) {
        val name = if (users.isNotEmpty()) {
            StringUtils.convertListStringsToString(users.map { it.name }, "-")
        } else {
            ""
        }
        toast(name)
    }

    override fun onGetError(throwable: Throwable) {
        (activity as BaseActivity).logError(TAG, "error", throwable)
    }

    override fun showDialogProgress(@StringRes stringResId: Int) {
        mDialogManager.showProgressDialog(getString(stringResId))
    }

    override fun dismissDialogProgress() {
        mDialogManager.dismissProgressDialog()
    }

    private fun handleEvents() {
        mView.nextView.setOnClickListener { onNextClick() }
    }

    private fun onNextClick() {
        if (mValidator.isValidEmail(
                mView.emailOrPhoneEditText.text.toString(),
                isCheckTempMails = true
            )
        ) {
            mPresenter.login(
                mView.emailOrPhoneEditText.text.toString().trim(),
                mView.passwordEditText.text.toString().trim()
            )
        } else {
            toast(getString(R.string.email_invalid))
        }
    }

    private fun toast(message: String) {
        (activity as BaseActivity).toast(message)
    }

    companion object {
        var TAG: String = this::class.java.simpleName

        fun newInstance(): MainLoginFragment {
            return MainLoginFragment()
        }
    }
}
