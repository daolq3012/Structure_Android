package com.fstyle.structure_android.screen.main;

import android.app.Activity;
import android.content.Context;
import com.fstyle.structure_android.data.source.UserRepository;
import com.fstyle.structure_android.utils.dagger.ActivityScope;
import com.fstyle.structure_android.utils.navigator.Navigator;
import com.fstyle.structure_android.utils.rx.CustomCompositeSubscription;
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
    public MainContract.ViewModel provideViewModel(MainContract.Presenter presenter,
            DialogManager dialogManager, Navigator navigator) {
        return new MainViewModel(presenter, dialogManager, navigator);
    }

    @ActivityScope
    @Provides
    public MainContract.Presenter providePresenter(UserRepository userRepository,
            Validator validator, CustomCompositeSubscription subscription) {
        return new MainPresenter(userRepository, validator, subscription);
    }

    @ActivityScope
    @Provides
    public Validator provideValidator(Context context) {
        return new Validator(context, MainViewModel.class);
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
