package com.fstyle.structure_android.screen.userdetail;

/**
 * Listens to user actions from the UI ({@link UserDetailActivity}), retrieves the data and updates
 * the UI as required.
 */
final class UserDetailPresenter implements UserDetailContract.Presenter {
    private static final String TAG = UserDetailPresenter.class.getName();

    private UserDetailContract.View mView;

    public UserDetailPresenter() {
    }

    @Override
    public void setView(UserDetailContract.View view) {
        mView = view;
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
    }
}
