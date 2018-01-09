package com.fstyle.structure_android.screen.userdetail;

import android.databinding.ObservableField;
import com.fstyle.structure_android.data.model.User;
import com.fstyle.structure_android.screen.BaseViewModel;

/**
 * Created by daolq on 1/9/18.
 */

public class UserDetailViewModel extends BaseViewModel {

    public ObservableField<User> mUserObservableField = new ObservableField<>(new User());

    public UserDetailViewModel(int userId) {
    }

    @Override
    protected void onStart() {

    }

    @Override
    protected void onStop() {

    }
}
