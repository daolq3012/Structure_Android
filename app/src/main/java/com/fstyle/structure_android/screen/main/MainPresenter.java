package com.fstyle.structure_android.screen.main;

import com.fstyle.structure_android.data.model.User;
import com.fstyle.structure_android.data.source.UserRepository;
import com.fstyle.structure_android.data.source.remote.api.error.BaseException;
import com.fstyle.structure_android.data.source.remote.api.error.RequestError;
import com.fstyle.structure_android.utils.common.StringUtils;
import com.fstyle.structure_android.utils.rx.BaseSchedulerProvider;
import java.util.List;
import rx.Subscription;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by le.quang.dao on 10/03/2017.
 */

class MainPresenter implements MainContract.Presenter {

    private MainContract.View mMainView;
    private UserRepository mUserRepository;
    private CompositeSubscription mCompositeSubscription;
    private BaseSchedulerProvider mSchedulerProvider;

    MainPresenter(UserRepository userRepository) {
        mUserRepository = userRepository;
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
    public void validateDataInput() {
        boolean isValid = validateKeywordInput(mMainView.getKeyword()) &&
                validateLimitNumberInput(mMainView.getLimitNumber());
        if (isValid) {
            mMainView.onDataValid();
        }
    }

    @Override
    public void searchUsers() {
        Subscription subscription = mUserRepository.searchUsers(mMainView.getLimitNumber(), mMainView.getKeyword())
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(new Action1<List<User>>() {
                    @Override
                    public void call(List<User> users) {
                        mMainView.onSearchUsersSuccess(users);
                    }
                }, new RequestError() {
                    @Override
                    public void onRequestError(BaseException error) {
                        mMainView.onSearchError(error);
                    }
                });
        mCompositeSubscription.add(subscription);
    }

    private boolean validateKeywordInput(String keyword) {
        if (StringUtils.isBlank(keyword)) {
            mMainView.onInvalidKeyWord("Keyword must not empty!");
        }
        return StringUtils.isBlank(keyword);
    }

    private boolean validateLimitNumberInput(String limit) {
        if (StringUtils.isBlank(limit)) {
            mMainView.onInvalidLimitNumber("Limit number must not empty!");
        }
        return StringUtils.isBlank(limit);
    }
}
