package com.fstyle.structure_android.screen.userdetail;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.fstyle.structure_android.R;
import com.fstyle.structure_android.data.model.User;
import com.fstyle.structure_android.databinding.ActivityUserDetailBinding;
import com.fstyle.structure_android.screen.BaseActivity;
import com.fstyle.structure_android.utils.Constant;

/**
 * UserDetail Screen.
 */
public class UserDetailActivity extends BaseActivity {

    private UserDetailViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int userId = getIntent().getIntExtra(Constant.ARGUMENT_USER_ID,-1);
        mViewModel = new UserDetailViewModel(userId);
        ActivityUserDetailBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_user_detail);
        binding.setViewModel(mViewModel);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mViewModel.onStart();
    }

    @Override
    protected void onStop() {
        mViewModel.onStop();
        super.onStop();
    }
}
