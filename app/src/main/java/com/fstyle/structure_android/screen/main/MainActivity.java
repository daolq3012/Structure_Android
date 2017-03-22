package com.fstyle.structure_android.screen.main;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import com.fstyle.structure_android.R;
import com.fstyle.structure_android.data.source.UserRepository;
import com.fstyle.structure_android.data.source.remote.UserRemoteDataSource;
import com.fstyle.structure_android.data.source.remote.api.service.NameServiceClient;
import com.fstyle.structure_android.databinding.ActivityMainBinding;
import com.fstyle.structure_android.screen.BaseActivity;
import com.fstyle.structure_android.utils.navigator.Navigator;
import com.fstyle.structure_android.utils.validator.Validator;
import com.fstyle.structure_android.widget.dialog.DialogManager;
import com.fstyle.structure_android.widget.dialog.DialogManagerImpl;

/**
 * Create by DaoLQ
 */
public class MainActivity extends BaseActivity {
    private static final String TAG = MainActivity.class.getName();

    private MainContract.ViewModel mMainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DialogManager dialogManager = new DialogManagerImpl(this);
        Navigator navigator = new Navigator(this);
        mMainViewModel = new MainViewModel(dialogManager, navigator);

        UserRepository userRepository =
                new UserRepository(null, new UserRemoteDataSource(NameServiceClient.getInstance()));
        Validator validator = new Validator(getApplicationContext(), mMainViewModel);
        MainContract.Presenter presenter =
                new MainPresenter(mMainViewModel, userRepository, validator);

        mMainViewModel.setPresenter(presenter);

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
