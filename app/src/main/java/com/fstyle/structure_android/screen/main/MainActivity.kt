package com.fstyle.structure_android.screen.main

import android.databinding.DataBindingUtil
import android.os.Bundle
import com.fstyle.structure_android.MainApplication
import com.fstyle.structure_android.R
import com.fstyle.structure_android.databinding.ActivityMainBinding
import com.fstyle.structure_android.screen.BaseActivity
import javax.inject.Inject

/**
 * Create by DaoLQ
 */
class MainActivity : BaseActivity() {

  @Inject
  lateinit var mMainViewModel: MainContract.ViewModel

  override fun onCreate(savedInstanceState: Bundle?) {
    DaggerMainComponent.builder()
        .appComponent((application as MainApplication).appComponent)
        .mainModule(MainModule(this))
        .build()
        .inject(this)
    super.onCreate(savedInstanceState)

    val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
    binding.viewModel = mMainViewModel as MainViewModel
  }

  override fun onStart() {
    super.onStart()
    mMainViewModel!!.onStart()
  }

  override fun onStop() {
    mMainViewModel!!.onStop()
    super.onStop()
  }
}
