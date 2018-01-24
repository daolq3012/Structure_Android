package com.fstyle.structure_android.screen.userdetail;

import com.fstyle.structure_android.data.repository.UserRepository;
import com.fstyle.structure_android.utils.rx.BaseSchedulerProvider;
import com.google.common.base.Preconditions;

/**
 * Listens to user actions from the UI ({@link UserDetailActivity}), retrieves the data and updates
 * the UI as required.
 */
final class UserDetailPresenter implements UserDetailContract.Presenter {
    private static final String TAG = UserDetailPresenter.class.getName();

    private UserDetailContract.View mView;

    private UserRepository mUserRepository;
    private BaseSchedulerProvider mSchedulerProvider;

    UserDetailPresenter(UserRepository userRepository,
            BaseSchedulerProvider schedulerProvider) {
        mUserRepository = Preconditions.checkNotNull(userRepository);
        mSchedulerProvider = Preconditions.checkNotNull(schedulerProvider);
    }

    @Override
    public void setView(UserDetailContract.View view) {
        mView = view;
    }

    @Override
    public void onStart() {
        loadUser();
    }

    private void loadUser() {
        mUserRepository.getUser(mView.getAvatarUrl())
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(user -> mView.showUser(user), error -> mView.showGetUserError(error));
    }

    @Override
    public void onStop() {
    }
}
