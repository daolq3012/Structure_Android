package com.fstyle.structure_android.screen.main;

import android.util.Log;
import com.fstyle.structure_android.data.model.UsersList;
import com.fstyle.structure_android.data.source.UserRepository;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by le.quang.dao on 10/03/2017.
 */

class MainPresenter implements MainContract.Presenter {
    private static final String TAG = MainPresenter.class.getName();

    private final MainContract.View mMainView;
    private UserRepository mUserRepository;

    MainPresenter(MainContract.View view, UserRepository userRepository) {
        mMainView = view;
        mUserRepository = userRepository;
        mMainView.setPresenter(this);
    }

    @Override
    public void onStart() {
        mUserRepository.getRemoteDataSource()
                .searchUsers("daolq")
                .subscribeOn(Schedulers.io())
                .subscribe(new Action1<UsersList>() {
                    @Override
                    public void call(UsersList usersList) {
                        Log.d(TAG, "call: " + usersList.toString());
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e(TAG, "call: ", throwable);
                    }
                });
    }

    @Override
    public void onStop() {

    }
}
