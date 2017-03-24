package com.fstyle.structure_android.screen.searchresult;

/**
 * Listens to user actions from the UI ({@link SearchResultActivity}), retrieves the data and
 * updates
 * the UI as required.
 */
final class SearchResultPresenter implements SearchResultContract.Presenter {
    private static final String TAG = SearchResultPresenter.class.getName();

    private SearchResultContract.ViewModel mViewModel;

    SearchResultPresenter() {
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
    }

    @Override
    public void setViewModel(SearchResultContract.ViewModel viewModel) {
        mViewModel = viewModel;
    }
}
