package com.fstyle.structure_android.screen.searchresult

import com.fstyle.structure_android.screen.BasePresenter
import com.fstyle.structure_android.screen.BaseViewModel

/**
 * This specifies the contract between the view and the presenter.
 */
interface SearchResultContract {
  /**
   * View.
   */
  interface ViewModel : BaseViewModel

  /**
   * Presenter.
   */
  interface Presenter : BasePresenter<ViewModel>
}
