package com.fstyle.structure_android.screen.main;

import com.fstyle.structure_android.data.model.User;
import com.fstyle.structure_android.data.source.UserRepository;
import com.fstyle.structure_android.utils.rx.BaseSchedulerProvider;
import com.fstyle.structure_android.utils.validator.Validator;
import java.util.List;
import rx.Subscription;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by le.quang.dao on 10/03/2017.
 */

public class MainPresenter implements MainContract.Presenter {
    private static final String TAG = MainPresenter.class.getName();

    private MainContract.View mMainView;
    private UserRepository mUserRepository;
    private Validator mValidator;
    private BaseSchedulerProvider mSchedulerProvider;
    private CompositeSubscription mCompositeSubscription;

    MainPresenter(MainContract.View view, UserRepository userRepository, Validator validator) {
        mMainView = view;
        mUserRepository = userRepository;
        mValidator = validator;
        mCompositeSubscription = new CompositeSubscription();

        mValidator.initNGWordPattern();
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {
        mCompositeSubscription.clear();
    }

    @Override
    public void setSchedulerProvider(BaseSchedulerProvider schedulerProvider) {
        mSchedulerProvider = schedulerProvider;
    }

    @Override
    public void searchUsers(int limit, String keyWord) {
        Subscription subscription = mUserRepository.searchUsers(limit, keyWord)
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(new Action1<List<User>>() {
                    @Override
                    public void call(List<User> users) {
                        mMainView.onSearchUsersSuccess(users);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mMainView.onSearchError(throwable);
                    }
                });
        mCompositeSubscription.add(subscription);
    }

    @Override
    public boolean validateDataInput(String keyWord, String limit) {
        if (mValidator.validateValueNonEmpty(keyWord)) {
            mValidator.validateNGWord(keyWord);
        }
        mMainView.onInvalidKeyWord(mValidator.getMessage());

        if (mValidator.validateValueNonEmpty(limit)) {
            mValidator.validateValueRangeFrom0to100(limit);
        }
        mMainView.onInvalidLimitNumber(mValidator.getMessage());

        try {
            return mValidator.validateAll();
        } catch (IllegalAccessException e) {
            return false;
        }
    }
}
