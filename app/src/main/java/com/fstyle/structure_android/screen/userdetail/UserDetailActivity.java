package com.fstyle.structure_android.screen.userdetail;

import android.os.Bundle;

import com.fstyle.structure_android.R;
import com.fstyle.structure_android.screen.BaseActivity;

/**
 * UserDetail Screen.
 */
public class UserDetailActivity extends BaseActivity implements UserDetailContract.View {

    UserDetailContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

        mPresenter = new UserDetailPresenter();
        mPresenter.setView(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.onStart();
    }

    @Override
    protected void onStop() {
        mPresenter.onStop();
        super.onStop();
    }
}
