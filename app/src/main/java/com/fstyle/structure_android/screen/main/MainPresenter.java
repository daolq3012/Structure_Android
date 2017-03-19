package com.fstyle.structure_android.screen.main;

import android.text.TextUtils;

import com.fstyle.structure_android.data.model.UsersList;
import com.fstyle.structure_android.data.source.UserRepository;
import com.fstyle.structure_android.utils.Constant;
import com.fstyle.structure_android.utils.validator.Validator;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by le.quang.dao on 10/03/2017.
 */

class MainPresenter implements MainContract.Presenter {
    private static final String TAG = MainPresenter.class.getName();

    private final MainContract.View mMainView;
    private UserRepository mUserRepository;
    private Validator mValidator;
    private final CompositeSubscription mCompositeSubscription;

    MainPresenter(MainContract.View view, UserRepository userRepository, Validator validator) {
        mMainView = view;
        mUserRepository = userRepository;
        mValidator = validator;

        mValidator.initNGWordPattern();
        mCompositeSubscription = new CompositeSubscription();
    }

    private boolean validateDataInput(int limit, String keyWord) {
        String errorMsg = mValidator.validateValueRangeFrom0to100(limit);
        mMainView.showInvalidLimit(TextUtils.isEmpty(errorMsg) ? null : errorMsg);

        errorMsg = mValidator.validateNGWord(keyWord);
        errorMsg += (TextUtils.isEmpty(errorMsg) ? "" : Constant.BREAK_LINE)
                + mValidator.validateValueNonEmpty(keyWord);
        mMainView.showInvalidUserName(TextUtils.isEmpty(errorMsg) ? null : errorMsg);

        return mValidator.validateAll(mMainView, false);
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {
        mCompositeSubscription.clear();
    }

    @Override
    public void searchUsers(int limit, String keyWord) {
        if (!validateDataInput(limit, keyWord)) {
            return;
        }
        Subscription subscription = mUserRepository.getRemoteDataSource()
                .searchUsers(limit, keyWord)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<UsersList>() {
                    @Override
                    public void call(UsersList usersList) {
                        mMainView.showListUser(usersList);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mMainView.showError(throwable);
                    }
                });
        mCompositeSubscription.add(subscription);
    }
}
