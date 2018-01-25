package com.fstyle.structure_android.screen.userlist;

import android.support.annotation.NonNull;
import com.fstyle.structure_android.data.model.User;
import com.fstyle.structure_android.data.repository.UserRepository;
import com.fstyle.structure_android.utils.rx.BaseSchedulerProvider;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Listens to user actions from the UI ({@link UserListActivity}), retrieves the data and
 * updates
 * the UI as required.
 */
final class UserListPresenter implements UserListContract.Presenter {

    private UserListContract.View mView;
    private UserRepository mUserRepository;
    private BaseSchedulerProvider mSchedulerProvider;

    @NonNull
    private CompositeDisposable mCompositeDisposable;

    UserListPresenter(UserRepository userRepository, BaseSchedulerProvider schedulerProvider) {
        mUserRepository = checkNotNull(userRepository, "UserRepository cannot be null!");
        mSchedulerProvider = checkNotNull(schedulerProvider, "schedulerProvider cannot be null");
        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void setView(UserListContract.View view) {
        mView = checkNotNull(view, "UserListContract.View cannot be null");
    }

    @Override
    public void onStart() {
        loadUsers(false);
    }

    @Override
    public void onStop() {
        mCompositeDisposable.clear();
    }

    @Override
    public void loadUsers(boolean forceUpdate) {
        loadUsers(forceUpdate, true);
    }

    @Override
    public void searchUsers(String userName) {
        Disposable disposable = mUserRepository.searchUsers(userName)
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())
                .doOnSubscribe(subscription -> mView.showLoadingIndicator())
                .doAfterTerminate(() -> mView.hideLoadingIndicator())
                .subscribe(this::processUsers, throwable -> mView.showLoadingUsersError(throwable));
        mCompositeDisposable.add(disposable);
    }

    private void loadUsers(boolean forceUpdate, boolean showLoading) {
        if (showLoading) {
            mView.showLoadingIndicator();
        }
        if (forceUpdate) {
            mUserRepository.refresh();
        }
        Disposable disposable = mUserRepository.getUsers()
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())
                .doFinally(() -> mView.hideLoadingIndicator())
                .subscribe(this::processUsers, throwable -> mView.showLoadingUsersError(throwable));
        mCompositeDisposable.add(disposable);
    }

    private void processUsers(List<User> users) {
        if (users.isEmpty()) {
            mView.showNoUser();
        } else {
            mView.showUsers(users);
        }
    }
}
