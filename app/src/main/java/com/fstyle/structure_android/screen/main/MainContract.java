package com.fstyle.structure_android.screen.main;

import com.fstyle.structure_android.data.model.User;
import com.fstyle.structure_android.screen.BasePresenter;
import com.fstyle.structure_android.screen.BaseViewModel;
import com.fstyle.structure_android.utils.rx.BaseSchedulerProvider;
import java.util.List;

/**
 * Created by le.quang.dao on 10/03/2017.
 */

public interface MainContract {

    /**
     * View
     */
    interface ViewModel extends BaseViewModel<Presenter> {
        void onSearchError(Throwable throwable);

        void onSearchUsersSuccess(List<User> users);

        void onInvalidKeyWord(String errorMsg);

        void onInvalidLimitNumber(String errorMsg);
    }

    /**
     * Presenter
     */
    interface Presenter extends BasePresenter {

        void setSchedulerProvider(BaseSchedulerProvider schedulerProvider);

        boolean validateKeywordInput(String keyword);

        boolean validateLimitNumberInput(String limit);

        boolean validateDataInput(String keyword, String limit);

        void searchUsers(String keyWord, int limit);
    }
}
