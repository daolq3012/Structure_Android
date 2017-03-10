package com.fstyle.structure_android.screen.main;

import android.os.Bundle;

import com.fstyle.structure_android.R;
import com.fstyle.structure_android.data.source.UserRepository;
import com.fstyle.structure_android.data.source.remote.UserRemoteDataSource;
import com.fstyle.structure_android.data.source.remote.api.service.NameServiceClient;
import com.fstyle.structure_android.screen.BaseActivity;

/**
 * Create by DaoLQ
 */
public class MainActivity extends BaseActivity implements MainContract.View {

    private MainContract.Presenter mPresenter;
    private UserRepository mUserRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mUserRepository = new UserRepository(null, new UserRemoteDataSource(NameServiceClient
                .getInstance()));
        new MainPresenter(this, mUserRepository);
    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {
        mPresenter = presenter;
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
