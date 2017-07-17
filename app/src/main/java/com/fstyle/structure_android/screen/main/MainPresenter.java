package com.fstyle.structure_android.screen.main;

import android.util.Log;
import com.fstyle.structure_android.data.model.User;
import com.fstyle.structure_android.data.source.UserRepository;
import com.fstyle.structure_android.data.source.remote.api.error.BaseException;
import com.fstyle.structure_android.data.source.remote.api.error.RequestError;
import com.fstyle.structure_android.utils.common.StringUtils;
import com.fstyle.structure_android.utils.rx.BaseSchedulerProvider;
import com.fstyle.structure_android.utils.validator.Validator;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.List;

/**
 * Created by le.quang.dao on 10/03/2017.
 */

class MainPresenter implements MainContract.Presenter {
    private static final String TAG = MainPresenter.class.getName();

    private MainContract.ViewModel mMainViewModel;
    private UserRepository mUserRepository;
    private Validator mValidator;
    private CompositeDisposable mCompositeSubscription;
    private BaseSchedulerProvider mSchedulerProvider;

    MainPresenter(UserRepository userRepository, Validator validator,
            BaseSchedulerProvider schedulerProvider) {
        mUserRepository = userRepository;
        mValidator = validator;
        mSchedulerProvider = schedulerProvider;
        mCompositeSubscription = new CompositeDisposable();
        Disposable subscription = mValidator.initNGWordPattern();
        if (subscription != null) {
            mCompositeSubscription.add(subscription);
        }
    }

    @Override
    public void onStart() {
    }

    @Override
    public void setSchedulerProvider(BaseSchedulerProvider schedulerProvider) {
        mSchedulerProvider = schedulerProvider;
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
    public void validateKeywordInput(String keyword) {
        String message = mValidator.validateValueNonEmpty(keyword);
        if (StringUtils.INSTANCE.isBlank(message)) {
            message = mValidator.validateNGWord(keyword);
        }
        mMainViewModel.onInvalidKeyWord(message);
    }

    @Override
    public void validateLimitNumberInput(String limit) {
        String message = mValidator.validateValueNonEmpty(limit);
        if (StringUtils.INSTANCE.isBlank(message)) {
            message = mValidator.validateValueRangeFrom0to100(limit);
        }
        mMainViewModel.onInvalidLimitNumber(message);
    }

    @Override
    public boolean validateDataInput(String keyword, String limit) {
        validateKeywordInput(keyword);
        validateLimitNumberInput(limit);
        try {
            return mValidator.validateAll(mMainViewModel);
        } catch (IllegalAccessException e) {
            Log.e(TAG, "validateDataInput: ", e);
            return false;
        }
    }

    @Override
    public void searchUsers(String keyWord, int limit) {
        Disposable subscription = mUserRepository.searchUsers(keyWord, limit)
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(new Consumer<List<User>>() {
                    @Override
                    public void accept(List<User> users) throws Exception {
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
