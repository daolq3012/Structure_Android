package com.fstyle.structure_android.screen.searchresult

import android.databinding.DataBindingUtil
import android.os.Bundle
import com.fstyle.structure_android.MainApplication
import com.fstyle.structure_android.R
import com.fstyle.structure_android.databinding.ActivitySearchResultBinding
import com.fstyle.structure_android.screen.BaseActivity
import javax.inject.Inject

/**
 * SearchResult Screen.
 */
class SearchResultActivity : BaseActivity() {

  @Inject
  internal lateinit var mViewModel: SearchResultContract.ViewModel

  override fun onCreate(savedInstanceState: Bundle?) {
    DaggerSearchResultComponent.builder()
        .appComponent((application as MainApplication).appComponent)
        .searchResultModule(SearchResultModule(this))
        .build()
        .inject(this)

    super.onCreate(savedInstanceState)

    val binding = DataBindingUtil.setContentView<ActivitySearchResultBinding>(this,
        R.layout.activity_search_result)
    binding.viewModel = mViewModel as SearchResultViewModel?
  }

  public override fun onStart() {
    super.onStart()
    mViewModel.onStart()
  }

  public override fun onStop() {
    mViewModel.onStop()
    super.onStop()
  }
}
