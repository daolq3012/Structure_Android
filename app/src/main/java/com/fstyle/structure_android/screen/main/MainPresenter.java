package com.fstyle.structure_android.screen.main;

import android.text.TextUtils;
import com.fstyle.structure_android.data.source.UserRepository;
import com.fstyle.structure_android.utils.Constant;
import com.fstyle.structure_android.utils.validator.Validator;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by le.quang.dao on 10/03/2017.
 */

class MainPresenter implements MainContract.Presenter {
    private static final String TAG = MainPresenter.class.getName();

    private MainContract.ViewModel mMainViewModel;
    private UserRepository mUserRepository;
    private Validator mValidator;
    private final CompositeSubscription mCompositeSubscription;

    MainPresenter(UserRepository userRepository, Validator validator) {
        mUserRepository = userRepository;
        mValidator = validator;
        mValidator.initNGWordPattern();
        mCompositeSubscription = new CompositeSubscription();
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
        mCompositeSubscription.clear();
    }

    @Override
    public void setViewModel(MainContract.ViewModel viewModel) {
        mMainViewModel = viewModel;
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
                .subscribe(users -> mMainViewModel.searchUsersSuccess(users),
                        throwable -> mMainViewModel.searchError(throwable));
        mCompositeSubscription.add(subscription);
    }

    private boolean validateDataInput(int limit, String keyWord) {
        String errorMsg = mValidator.validateNGWord(keyWord);
        errorMsg += (TextUtils.isEmpty(errorMsg) ? "" : Constant.BREAK_LINE)
                + mValidator.validateValueNonEmpty(keyWord);
        mMainViewModel.invalidKeyWord(TextUtils.isEmpty(errorMsg) ? null : errorMsg);

        errorMsg = mValidator.validateValueRangeFrom0to100(limit);
        mMainViewModel.invalidLimitNumber(TextUtils.isEmpty(errorMsg) ? null : errorMsg);

        return mValidator.validateAll(mMainViewModel, false);
    }
}
