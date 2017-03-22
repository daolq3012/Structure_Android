package com.fstyle.structure_android.screen.searchresult;

import com.fstyle.structure_android.data.model.User;
import com.fstyle.structure_android.screen.BasePresenter;
import com.fstyle.structure_android.screen.BaseViewModel;

/**
 * This specifies the contract between the view and the presenter.
 */
interface SearchResultContract {
    /**
     * View.
     */
    interface ViewModel extends BaseViewModel {
    }

    /**
     * Presenter.
     */
    interface Presenter extends BasePresenter<ViewModel> {

        void onItemUserClicked(User user);
    }
}
