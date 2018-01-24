package com.fstyle.structure_android.screen.edituser;

import com.fstyle.structure_android.data.model.User;
import com.fstyle.structure_android.screen.BasePresenter;

/**
 * Created by le.quang.dao on 10/03/2017.
 */

public interface EditUserContract {

    /**
     * View.
     */
    interface View {
        String getAvatarUrl();

        String getUserLoginInput();

        void showUser(User user);

        void showGetUserError(Throwable throwable);

        void showUpdateUserError(Throwable throwable);

        void showListUser();
    }

    /**
     * Presenter.
     */
    interface Presenter extends BasePresenter<EditUserContract.View> {
        void updateUser();
    }
}
