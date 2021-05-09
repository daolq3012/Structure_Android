package com.ccc.nameapp.scenes.login.main

import com.ccc.nameapp.R
import com.ccc.nameapp.repositories.TokenRepository
import com.ccc.nameapp.repositories.UserRepository
import com.ccc.nameapp.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Listens to user actions from the UI ({@link MainLoginFragment}), retrieves the data and updates
 * the UI as required.
 */
class MainLoginPresenterImpl @Inject constructor(
    private var view: MainLoginView?,
    private val schedulerProvider: SchedulerProvider,
    private val userRepository: UserRepository,
    private val tokenRepository: TokenRepository
) : MainLoginPresenter {

    private val mCompositeDisposable = CompositeDisposable()

    override fun onStart() {
        // No-op
    }

    override fun onStop() {
        // No-op
    }

    override fun onDestroy() {
        mCompositeDisposable.clear()
        view = null
    }

    override fun login(email: String, password: String) {
        val disposable = userRepository.login(email, password)
            .subscribeOn(schedulerProvider.io())
            .doOnSubscribe { view?.showDialogProgress(R.string.processing) }
            .map {
                tokenRepository.saveToken(it)
            }
            .observeOn(schedulerProvider.ui())
            .doAfterTerminate { view?.dismissDialogProgress() }
            .subscribe({
                view?.loginSuccess()
            }, {
                view?.onGetError(it)
            })
        mCompositeDisposable.add(disposable)
    }
}
