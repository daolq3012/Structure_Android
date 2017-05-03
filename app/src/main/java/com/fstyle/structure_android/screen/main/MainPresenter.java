package com.fstyle.structure_android.screen.main;

import com.fstyle.structure_android.data.model.User;
import com.fstyle.structure_android.data.source.UserRepository;
import com.fstyle.structure_android.utils.common.StringUtils;
import com.fstyle.structure_android.utils.rx.BaseSchedulerProvider;
import com.fstyle.structure_android.utils.validator.Validator;
import java.util.List;
import rx.Subscription;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by le.quang.dao on 10/03/2017.
 */

class MainPresenter implements MainContract.Presenter {
    private static final String TAG = MainPresenter.class.getName();

    private MainContract.View mMainView;
    private UserRepository mUserRepository;
    private CompositeSubscription mCompositeSubscription;
    private BaseSchedulerProvider mSchedulerProvider;
    private Validator mValidator;

    MainPresenter(UserRepository userRepository, Validator validator) {
        mUserRepository = userRepository;
        mValidator = validator;
        mValidator.initNGWordPattern();
        mCompositeSubscription = new CompositeSubscription();
    }

    @Override
    public void setSchedulerProvider(BaseSchedulerProvider schedulerProvider) {
        mSchedulerProvider = schedulerProvider;
    }

    @Override
    public void setView(MainContract.View view) {
        mMainView = view;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {
        mCompositeSubscription.clear();
    }

    @Override
    public boolean validateKeywordInput(String keyword) {
        String message = mValidator.validateValueNonEmpty(keyword);
        if (StringUtils.isBlank(message)) {
            message = mValidator.validateNGWord(keyword);
        }
        mMainView.onInvalidKeyWord(message);
        return StringUtils.isBlank(message);
    }

    @Override
    public boolean validateLimitNumberInput(String limit) {
        String message = mValidator.validateValueNonEmpty(limit);
        if (StringUtils.isBlank(message)) {
            message = mValidator.validateValueRangeFrom0to100(limit);
        }
        mMainView.onInvalidLimitNumber(message);
        return StringUtils.isBlank(message);
    }

    @Override
    public boolean validateDataInput(String keyword, String limit) {
        validateKeywordInput(keyword);
        validateLimitNumberInput(limit);
        try {
            return mValidator.validateAll();
        } catch (IllegalAccessException e) {
            return false;
        }
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
}
