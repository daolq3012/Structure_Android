package com.fstyle.structure_android.screen.searchresult;

import android.util.Log;
import com.fstyle.structure_android.data.model.User;

/**
 * Listens to user actions from the UI ({@link SearchResultActivity}), retrieves the data and
 * updates
 * the UI as required.
 */
final class SearchResultPresenter implements SearchResultContract.Presenter {
    private static final String TAG = SearchResultPresenter.class.getName();

    private final SearchResultContract.ViewModel mViewModel;

    SearchResultPresenter(SearchResultContract.ViewModel viewModel) {
        mViewModel = viewModel;
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
    }

    @Override
    public void onItemUserClicked(User user) {
        Log.d(TAG, "onItemUserClicked: " + user.getLogin());
    }
}
