package com.fstyle.structure_android.screen.main;

import com.fstyle.structure_android.data.model.UsersList;
import com.fstyle.structure_android.screen.BasePresenter;
import com.fstyle.structure_android.screen.BaseView;

/**
 * Created by le.quang.dao on 10/03/2017.
 */

public interface MainContract {

    /**
     * View
     */
    interface View extends BaseView<Presenter> {
        void showError(Throwable throwable);

        void showListUser(UsersList usersList);
    }

    /**
     * Presenter
     */
    interface Presenter extends BasePresenter {
        void searchUsers(int limit, String term);
    }
}
