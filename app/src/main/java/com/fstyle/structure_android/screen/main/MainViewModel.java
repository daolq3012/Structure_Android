package com.fstyle.structure_android.screen.main;

import android.databinding.Bindable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.view.View;
import com.fstyle.library.DialogAction;
import com.fstyle.library.MaterialDialog;
import com.fstyle.structure_android.BR;
import com.fstyle.structure_android.R;
import com.fstyle.structure_android.data.model.User;
import com.fstyle.structure_android.data.source.UserRepository;
import com.fstyle.structure_android.data.source.remote.api.error.BaseException;
import com.fstyle.structure_android.data.source.remote.api.error.RequestError;
import com.fstyle.structure_android.screen.BaseViewModel;
import com.fstyle.structure_android.screen.searchresult.SearchResultActivity;
import com.fstyle.structure_android.utils.Constant;
import com.fstyle.structure_android.utils.common.StringUtils;
import com.fstyle.structure_android.utils.navigator.Navigator;
import com.fstyle.structure_android.utils.rx.BaseSchedulerProvider;
import com.fstyle.structure_android.utils.validator.Rule;
import com.fstyle.structure_android.utils.validator.ValidType;
import com.fstyle.structure_android.utils.validator.Validation;
import com.fstyle.structure_android.utils.validator.Validator;
import com.fstyle.structure_android.widget.dialog.DialogManager;
import java.util.ArrayList;
import java.util.List;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by Sun on 3/19/2017.
 * Relative @{@link MainActivity}
 */

public class MainViewModel extends BaseViewModel {
    private Validator mValidator;
    private UserRepository mUserRepository;
    private DialogManager mDialogManager;
    private Navigator mNavigator;
    private BaseSchedulerProvider mSchedulerProvider;

    @Validation({
            @Rule(types = {
                    ValidType.NG_WORD, ValidType.NON_EMPTY
            }, message = R.string.error_unusable_characters)
    })
    private String mKeyWord;
    @Validation({
            @Rule(types = ValidType.VALUE_RANGE_0_100, message = R.string
                    .error_lenght_from_0_to_100),
            @Rule(types = ValidType.NON_EMPTY, message = R.string.must_not_empty)
    })
    private String mLimit;
    private String mKeywordErrorMsg;
    private String mLimitErrorMsg;

    public MainViewModel(UserRepository mUserRepository, DialogManager dialogManager,
            Navigator navigator) {
        this.mUserRepository = mUserRepository;
        this.mDialogManager = dialogManager;
        this.mNavigator = navigator;
    }

    @Override
    public void setSchedulerProvider(BaseSchedulerProvider schedulerProvider) {
        mSchedulerProvider = schedulerProvider;
    }

    public void setValidator(Validator validator) {
        mValidator = validator;
    }

    @Bindable
    public String getKeyWord() {
        return mKeyWord;
    }

    public void setKeyWord(String keyWord) {
        mKeyWord = keyWord;
        validateKeywordInput(keyWord);
    }

    @Bindable
    public String getLimit() {
        return mLimit;
    }

    public void setLimit(String limit) {
        mLimit = limit;
        validateLimitNumberInput(limit);
    }

    @Bindable
    public String getKeywordErrorMsg() {
        return mKeywordErrorMsg;
    }

    public void setKeywordErrorMsg(String keywordErrorMsg) {
        mKeywordErrorMsg = keywordErrorMsg;
    }

    @Bindable
    public String getLimitErrorMsg() {
        return mLimitErrorMsg;
    }

    public void setLimitErrorMsg(String limitErrorMsg) {
        mLimitErrorMsg = limitErrorMsg;
    }

    @Bindable
    public boolean isEnableSearchButton() {
        try {
            return mValidator.validateAll();
        } catch (IllegalAccessException e) {
            return false;
        }
    }

    public void onSearchButtonClicked(View view) {
        callAPISearchUsers();
    }

    private void callAPISearchUsers() {
        mDialogManager.showIndeterminateProgressDialog();
        Subscription subscription =
                mUserRepository.searchUsers(mKeyWord, StringUtils.convertStringToNumber(mLimit))
                        .subscribeOn(mSchedulerProvider.io())
                        .observeOn(mSchedulerProvider.ui())
                        .subscribe(new Action1<List<User>>() {
                            @Override
                            public void call(List<User> users) {
                                mDialogManager.dismissProgressDialog();
                                gotoSearchResultActivity(users);
                            }
                        }, new RequestError() {
                            @Override
                            public void onRequestError(BaseException error) {
                                mDialogManager.dismissProgressDialog();
                                showDialogError(error);
                            }
                        });
        startSubscriber(subscription);
    }

    public void showDialogError(BaseException e) {
        mDialogManager.dialogError(e.getMessage(), new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog materialDialog,
                    @NonNull DialogAction dialogAction) {
                callAPISearchUsers();
            }
        });
    }

    public void gotoSearchResultActivity(List<User> users) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(Constant.ARGUMENT_LIST_USER,
                (ArrayList<? extends Parcelable>) users);
        mNavigator.startActivity(SearchResultActivity.class, bundle);
    }

    public void validateKeywordInput(String keyWord) {
        mKeywordErrorMsg = mValidator.validateValueNonEmpty(keyWord);
        if (StringUtils.isBlank(mKeywordErrorMsg)) {
            mKeywordErrorMsg = mValidator.validateNGWord(keyWord);
        }
        notifyPropertyChanged(BR.keywordErrorMsg);
        notifyPropertyChanged(BR.enableSearchButton);
    }

    public void validateLimitNumberInput(String limitNumber) {
        mLimitErrorMsg = mValidator.validateValueNonEmpty(limitNumber);
        if (StringUtils.isBlank(mLimitErrorMsg)) {
            mLimitErrorMsg = mValidator.validateValueRangeFrom0to100(limitNumber);
        }
        notifyPropertyChanged(BR.limitErrorMsg);
        notifyPropertyChanged(BR.enableSearchButton);
    }
}
