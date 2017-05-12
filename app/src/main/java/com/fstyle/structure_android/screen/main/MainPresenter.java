package com.fstyle.structure_android.screen.main;

import com.fstyle.structure_android.data.model.User;
import com.fstyle.structure_android.data.source.UserRepository;
import com.fstyle.structure_android.data.source.remote.api.error.BaseException;
import com.fstyle.structure_android.data.source.remote.api.error.RequestError;
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

    private MainContract.ViewModel mMainViewModel;
    private UserRepository mUserRepository;
    private CompositeSubscription mCompositeSubscription;
    private BaseSchedulerProvider mSchedulerProvider;
    private Validator mValidator;

    MainPresenter(MainContract.ViewModel viewModel, UserRepository userRepository,
            Validator validator) {
        mMainViewModel = viewModel;
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
    public void onStart() {

    }

    @Override
    public void onStop() {
        mCompositeSubscription.clear();
    }

    @Override
    public void validateKeywordInput(String keyword) {
        String message = mValidator.validateValueNonEmpty(keyword);
        if (StringUtils.isBlank(message)) {
            message = mValidator.validateNGWord(keyword);
        }
        mMainViewModel.onInvalidKeyWord(message);
    }

    @Override
    public void validateLimitNumberInput(String limit) {
        String message = mValidator.validateValueNonEmpty(limit);
        if (StringUtils.isBlank(message)) {
            message = mValidator.validateValueRangeFrom0to100(limit);
        }
        mMainViewModel.onInvalidLimitNumber(message);
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
    public void searchUsers(String keyWord, int limit) {
        Subscription subscription = mUserRepository.searchUsers(keyWord, limit)
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(new Action1<List<User>>() {
                    @Override
                    public void call(List<User> users) {
                        mMainViewModel.onSearchUsersSuccess(users);
                    }
                }, new RequestError() {
                    @Override
                    public void onRequestError(BaseException error) {
                        mMainViewModel.onSearchError(error);
                    }
                });
        mCompositeSubscription.add(subscription);
    }
}
