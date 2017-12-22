package com.fstyle.structure_android.screen.searchresult;

import com.fstyle.structure_android.screen.BasePresenter;

/**
 * This specifies the contract between the view and the presenter.
 */
interface SearchResultContract {
    /**
     * View.
     */
    interface View {
    }

    /**
     * Presenter.
     */
    interface Presenter extends BasePresenter<View> {
    }
}
