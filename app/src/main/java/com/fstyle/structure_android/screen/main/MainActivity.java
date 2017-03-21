package com.fstyle.structure_android.screen.main;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import com.fstyle.structure_android.MainApplication;
import com.fstyle.structure_android.R;
import com.fstyle.structure_android.databinding.ActivityMainBinding;
import com.fstyle.structure_android.screen.BaseActivity;
import javax.inject.Inject;

/**
 * Create by DaoLQ
 */
public class MainActivity extends BaseActivity {
    private static final String TAG = MainActivity.class.getName();

    @Inject
    MainContract.ViewModel mMainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DaggerMainComponent.builder()
                .appComponent(((MainApplication) getApplication()).getAppComponent())
                .mainModule(new MainModule(this))
                .build()
                .inject(this);
        super.onCreate(savedInstanceState);

        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setViewModel((MainViewModel) mMainViewModel);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mMainViewModel.onStart();
    }

    @Override
    protected void onStop() {
        mMainViewModel.onStop();
        super.onStop();
    }
}
