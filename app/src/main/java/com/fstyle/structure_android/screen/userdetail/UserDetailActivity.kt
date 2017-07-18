package com.fstyle.structure_android.screen.userdetail;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import com.fstyle.structure_android.MainApplication;
import com.fstyle.structure_android.R;
import com.fstyle.structure_android.databinding.ActivityUserDetailBinding
import com.fstyle.structure_android.screen.BaseActivity;
import javax.inject.Inject;

/**
 * UserDetail Screen.
 */
class UserDetailActivity : BaseActivity() {

  @Inject
  internal lateinit var mViewModel: UserDetailContract.ViewModel

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    DaggerUserDetailComponent.builder()
        .appComponent((application as MainApplication).appComponent)
        .userDetailModule(UserDetailModule(this))
        .build()
        .inject(this)

    val binding = DataBindingUtil.setContentView<ActivityUserDetailBinding>(this,
        R.layout.activity_user_detail)
    binding.viewModel = mViewModel as UserDetailViewModel?
  }

  override fun onStart() {
    super.onStart()
    mViewModel.onStart()
  }

  override fun onStop() {
    mViewModel.onStop()
    super.onStop()
  }
}
