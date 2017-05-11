package com.fstyle.structure_android.screen.main;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import com.fstyle.structure_android.BR;
import com.fstyle.structure_android.R;
import com.fstyle.structure_android.data.model.User;
import com.fstyle.structure_android.screen.searchresult.SearchResultActivity;
import com.fstyle.structure_android.utils.common.StringUtils;
import com.fstyle.structure_android.utils.navigator.Navigator;
import com.fstyle.structure_android.utils.validator.Rule;
import com.fstyle.structure_android.utils.validator.ValidType;
import com.fstyle.structure_android.utils.validator.Validation;
import com.fstyle.structure_android.widget.dialog.DialogManager;
import java.util.ArrayList;
import java.util.List;

import static com.fstyle.structure_android.utils.Constant.ARGUMENT_LIST_USER;

/**
 * Created by Sun on 3/20/2017.
 */

public class MainViewModel extends BaseObservable implements MainContract.ViewModel {

    private DialogManager mDialogManager;
    private Navigator mNavigator;

    private MainContract.Presenter mPresenter;

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

    public MainViewModel(MainContract.Presenter presenter, DialogManager dialogManager,
            Navigator navigator) {
        this.mPresenter = presenter;
        mPresenter.setViewModel(this);
        this.mDialogManager = dialogManager;
        this.mNavigator = navigator;
    }

    @Override
    public void onStart() {
        mPresenter.onStart();
    }

    @Override
    public void onStop() {
        mPresenter.onStop();
    }

    @Override
    public void onSearchError(Throwable throwable) {
        mDialogManager.dismissProgressDialog();
        mDialogManager.dialogError(throwable.getMessage(),
                (dialog, which) -> onSearchButtonClicked(null));
    }

    @Override
    public void onSearchUsersSuccess(List<User> users) {
        mDialogManager.dismissProgressDialog();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(ARGUMENT_LIST_USER, (ArrayList<? extends Parcelable>) users);
        mNavigator.startActivity(SearchResultActivity.class, bundle);
    }

    @Override
    public void onInvalidKeyWord(String errorMsg) {
        setKeywordErrorMsg(errorMsg);
    }

    @Override
    public void onInvalidLimitNumber(String errorMsg) {
        setLimitErrorMsg(errorMsg);
    }

    @Bindable
    public String getKeyWord() {
        return mKeyWord;
    }

    public void setKeyWord(String keyWord) {
        mKeyWord = keyWord;
        mPresenter.validateKeywordInput(keyWord);
    }

    @Bindable
    public String getLimit() {
        return mLimit;
    }

    public void setLimit(String limit) {
        mLimit = limit;
        mPresenter.validateLimitNumberInput(limit);
    }

    @Bindable
    public String getKeywordErrorMsg() {
        return mKeywordErrorMsg;
    }

    public void setKeywordErrorMsg(String keywordErrorMsg) {
        mKeywordErrorMsg = keywordErrorMsg;
        notifyPropertyChanged(BR.keywordErrorMsg);
    }

    @Bindable
    public String getLimitErrorMsg() {
        return mLimitErrorMsg;
    }

    public void setLimitErrorMsg(String limitErrorMsg) {
        mLimitErrorMsg = limitErrorMsg;
        notifyPropertyChanged(BR.limitErrorMsg);
    }

    public void onSearchButtonClicked(View view) {
        if (!mPresenter.validateDataInput(mKeyWord, mLimit)) {
            return;
        }
        mDialogManager.showIndeterminateProgressDialog();
        mPresenter.searchUsers(mKeyWord, StringUtils.convertStringToNumber(mLimit));
    }
}
