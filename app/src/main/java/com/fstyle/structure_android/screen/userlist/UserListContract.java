package com.fstyle.structure_android.screen.userlist;

import com.fstyle.structure_android.data.model.User;
import com.fstyle.structure_android.screen.BasePresenter;
import java.util.List;

/**
 * This specifies the contract between the view and the presenter.
 */
interface UserListContract {
    /**
     * View.
     */
    interface View {
        // Response from Presenter
        void showLoadingIndicator();

        void hideLoadingIndicator();

        void showLoadingUsersError(Throwable throwable);

        void showUsers(List<User> users);

        void showNoUser();
    }

    /**
     * Presenter.
     */
    interface Presenter extends BasePresenter<View> {
        void loadUsers(boolean forceUpdate);

        void searchUsers(String newText);
    }
}
