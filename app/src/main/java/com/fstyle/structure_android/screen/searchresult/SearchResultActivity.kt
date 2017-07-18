package com.fstyle.structure_android.screen.searchresult

import android.databinding.DataBindingUtil
import android.os.Bundle
import com.fstyle.structure_android.MainApplication
import com.fstyle.structure_android.R
import com.fstyle.structure_android.data.model.User
import com.fstyle.structure_android.databinding.ActivitySearchResultBinding
import com.fstyle.structure_android.screen.BaseActivity
import com.fstyle.structure_android.screen.userdetail.UserDetailActivity
import com.fstyle.structure_android.utils.Constant
import com.fstyle.structure_android.utils.navigator.Navigator
import javax.inject.Inject

/**
 * SearchResult Screen.
 */
class SearchResultActivity : BaseActivity(), SearchResultContract.ViewModel, ItemUserClickListener {

  @Inject
  internal lateinit var presenter: SearchResultContract.Presenter
  @Inject
  internal lateinit var adapter: SearchResultAdapter
  @Inject
  internal lateinit var navigator: Navigator

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    DaggerSearchResultComponent.builder()
        .appComponent((application as MainApplication).appComponent)
        .searchResultModule(SearchResultModule(this))
        .build()
        .inject(this)

    val binding = DataBindingUtil.setContentView<ActivitySearchResultBinding>(this,
        R.layout.activity_search_result)
    binding.viewModel = this
  }

  override fun onStart() {
    super.onStart()
    presenter.onStart()
  }

  override fun onStop() {
    presenter.onStop()
    super.onStop()
  }

  override fun onItemUserClick(user: User) {
    val bundle: Bundle = Bundle()
    bundle.putString(Constant.ARGUMENT_USER_LOGIN, user.login)
    navigator.startActivity(UserDetailActivity::class.java, bundle)
  }
}
