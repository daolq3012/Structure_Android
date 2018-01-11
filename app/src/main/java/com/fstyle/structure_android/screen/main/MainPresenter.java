package com.fstyle.structure_android.screen.main;

import com.fstyle.structure_android.data.model.User;
import com.fstyle.structure_android.data.repository.UserRepository;
import com.fstyle.structure_android.data.source.remote.config.error.BaseException;
import com.fstyle.structure_android.utils.common.StringUtils;
import com.fstyle.structure_android.utils.rx.BaseSchedulerProvider;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by le.quang.dao on 10/03/2017.
 */

class MainPresenter implements MainContract.Presenter {

    private MainContract.View mMainView;
    private UserRepository mUserRepository;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private BaseSchedulerProvider mSchedulerProvider;

    MainPresenter(UserRepository userRepository) {
        mUserRepository = userRepository;
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
        mCompositeDisposable.clear();
    }

    @Override
    public void validateDataInput() {
        boolean isValid = validateKeywordInput(mMainView.getKeyword()) & validateLimitNumberInput(
                mMainView.getLimitNumber());
        if (isValid) {
            mMainView.onDataValid();
        }
    }

    @Override
    public void searchUsers() {
        Disposable subscription =
                mUserRepository.getAllUser()
                        .subscribeOn(mSchedulerProvider.io())
                        .observeOn(mSchedulerProvider.ui())
                        .subscribe(new Consumer<List<User>>() {
                            @Override
                            public void accept(List<User> users) {
                                mMainView.onSearchUsersSuccess(users);
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                mMainView.onSearchError((BaseException) throwable);
                            }
                        });
        mCompositeDisposable.add(subscription);
    }

    private boolean validateKeywordInput(String keyword) {
        if (StringUtils.isBlank(keyword)) {
            mMainView.onInvalidKeyWord("Keyword must not empty!");
        }
        return !StringUtils.isBlank(keyword);
    }

    private boolean validateLimitNumberInput(String limit) {
        if (StringUtils.isBlank(limit)) {
            mMainView.onInvalidLimitNumber("Limit number must not empty!");
        }
        return !StringUtils.isBlank(limit);
    }
}
