package com.fstyle.structure_android.screen.main;

import android.app.Activity;
import android.content.Context;
import com.fstyle.structure_android.data.source.UserRepository;
import com.fstyle.structure_android.data.source.local.realm.UserLocalDataSource;
import com.fstyle.structure_android.data.source.remote.UserRemoteDataSource;
import com.fstyle.structure_android.utils.dagger.ActivityScope;
import com.fstyle.structure_android.utils.navigator.Navigator;
import com.fstyle.structure_android.utils.rx.BaseSchedulerProvider;
import com.fstyle.structure_android.utils.validator.Validator;
import com.fstyle.structure_android.widget.dialog.DialogManager;
import com.fstyle.structure_android.widget.dialog.DialogManagerImpl;
import dagger.Module;
import dagger.Provides;

/**
 * Created by le.quang.dao on 21/03/2017.
 */

@Module
public class MainModule {

    private Activity mActivity;

    public MainModule(Activity activity) {
        this.mActivity = activity;
    }

    @ActivityScope
    @Provides
    public Validator provideValidator(Context context) {
        return new Validator(context, MainViewModel.class);
    }

    @ActivityScope
    @Provides
    public MainContract.ViewModel provideViewModel(MainContract.Presenter presenter,
            DialogManager dialogManager, Navigator navigator) {
        return new MainViewModel(presenter, dialogManager, navigator);
    }

    @ActivityScope
    @Provides
    public MainContract.Presenter providePresenter(UserRepository userRepository,
            Validator validator, BaseSchedulerProvider schedulerProvider) {
        return new MainPresenter(userRepository, validator, schedulerProvider);
    }

    @ActivityScope
    @Provides
    public UserRepository provideUserRepository(UserLocalDataSource localDataSource,
            UserRemoteDataSource remoteDataSource) {
        return new UserRepository(localDataSource, remoteDataSource);
    }

    @ActivityScope
    @Provides
    public DialogManager provideDialogManager() {
        return new DialogManagerImpl(mActivity);
    }

    @ActivityScope
    @Provides
    public Navigator provideNavigator() {
        return new Navigator(mActivity);
    }
}
