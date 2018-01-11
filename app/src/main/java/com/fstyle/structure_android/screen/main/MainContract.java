package com.fstyle.structure_android.screen.main;

import com.fstyle.structure_android.data.model.User;
import com.fstyle.structure_android.data.source.remote.config.error.BaseException;
import com.fstyle.structure_android.screen.BasePresenter;
import com.fstyle.structure_android.utils.rx.BaseSchedulerProvider;

import java.util.List;

/**
 * Created by le.quang.dao on 10/03/2017.
 */

public interface MainContract {

    /**
     * View
     */
    interface View {

        String getKeyword();

        String getLimitNumber();

        // Response from Presenter
        void onSearchError(BaseException throwable);

        void onSearchUsersSuccess(List<User> users);

        void onInvalidKeyWord(String errorMsg);

        void onInvalidLimitNumber(String errorMsg);

        void onDataValid();
    }

    /**
     * Presenter
     */
    interface Presenter extends BasePresenter<View> {

        void setSchedulerProvider(BaseSchedulerProvider schedulerProvider);

        void validateDataInput();

        void searchUsers();
    }
}
