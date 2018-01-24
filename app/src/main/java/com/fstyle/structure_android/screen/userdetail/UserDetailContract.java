package com.fstyle.structure_android.screen.userdetail;

import com.fstyle.structure_android.data.model.User;
import com.fstyle.structure_android.screen.BasePresenter;

/**
 * This specifies the contract between the view and the presenter.
 */
interface UserDetailContract {
    /**
     * View.
     */
    interface View {
        String getAvatarUrl();

        void showUser(User user);

        void showGetUserError(Throwable throwable);
    }

    /**
     * Presenter.
     */
    interface Presenter extends BasePresenter<View> {
    }
}
