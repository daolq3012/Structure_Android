package com.fstyle.structure_android.screen.edituser;

import com.fstyle.structure_android.data.model.User;
import com.fstyle.structure_android.data.repository.UserRepository;
import com.fstyle.structure_android.utils.rx.BaseSchedulerProvider;
import com.google.common.base.Preconditions;

/**
 * Created by le.quang.dao on 10/03/2017.
 */

class EditUserPresenter implements EditUserContract.Presenter {

    private EditUserContract.View mView;

    private UserRepository mUserRepository;
    private BaseSchedulerProvider mSchedulerProvider;
    private User mUser;

    EditUserPresenter(UserRepository userRepository, BaseSchedulerProvider schedulerProvider) {
        mUserRepository = Preconditions.checkNotNull(userRepository);
        mSchedulerProvider = Preconditions.checkNotNull(schedulerProvider);
    }

    @Override
    public void setView(EditUserContract.View view) {
        mView = view;
    }

    @Override
    public void onStart() {
        loadUser();
    }

    @Override
    public void updateUser() {
        if (mUser.getLogin().equals(mView.getUserLoginInput())) {
            mView.showListUser();
            return;
        }
        mUser.setLogin(mView.getUserLoginInput());
        mUserRepository.updateUser(mUser)
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(() -> mView.showListUser(),
                        throwable -> mView.showUpdateUserError(throwable));
    }

    @Override
    public void onStop() {
    }

    private void loadUser() {
        mUserRepository.getUser(mView.getAvatarUrl())
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(user -> {
                    mUser = user;
                    mView.showUser(user);
                }, error -> mView.showGetUserError(error));
    }
}
