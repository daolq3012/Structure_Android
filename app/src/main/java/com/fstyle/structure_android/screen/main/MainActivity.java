package com.fstyle.structure_android.screen.main;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import com.fstyle.structure_android.R;
import com.fstyle.structure_android.data.repository.UserRepository;
import com.fstyle.structure_android.data.source.local.UserLocalDataSource;
import com.fstyle.structure_android.data.source.remote.UserRemoteDataSource;
import com.fstyle.structure_android.databinding.ActivityMainBinding;
import com.fstyle.structure_android.screen.BaseActivity;
import com.fstyle.structure_android.utils.navigator.Navigator;
import com.fstyle.structure_android.utils.rx.SchedulerProvider;
import com.fstyle.structure_android.widget.dialog.DialogManager;
import com.fstyle.structure_android.widget.dialog.DialogManagerImpl;

/**
 * Create by DaoLQ
 */
public class MainActivity extends BaseActivity {

    private MainViewModel mMainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UserRepository userRepository =
                UserRepository.getInstance(UserLocalDataSource.getInstance(), UserRemoteDataSource.getInstance());
        DialogManager dialogManager = new DialogManagerImpl(this);
        Navigator navigator = new Navigator(this);
        mMainViewModel = new MainViewModel(userRepository, dialogManager, navigator);

        mMainViewModel.setSchedulerProvider(SchedulerProvider.getInstance());

        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setViewModel(mMainViewModel);
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
