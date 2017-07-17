package com.fstyle.structure_android.screen.searchresult

/**
 * Listens to user actions from the UI ([SearchResultActivity]), retrieves the data and
 * updates
 * the UI as required.
 */
class SearchResultPresenter : SearchResultContract.Presenter {

  private var mViewModel: SearchResultContract.ViewModel? = null

  override fun onStart() {}

  override fun onStop() {}

  override fun setViewModel(viewModel: SearchResultContract.ViewModel) {
    mViewModel = viewModel
  }

  companion object {
    private val TAG = SearchResultPresenter::class.java.name
  }
}
