package com.fstyle.structure_android.screen.main;

import android.content.DialogInterface;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.View;
import com.fstyle.structure_android.BR;
import com.fstyle.structure_android.R;
import com.fstyle.structure_android.data.model.User;
import com.fstyle.structure_android.screen.searchresult.SearchResultActivity;
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
    private int mLimit = Integer.MIN_VALUE;
    private String mKeywordErrorMsg;
    private String mLimitErrorMsg;

    public MainViewModel(DialogManager dialogManager, Navigator navigator) {
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
    public void setPresenter(MainContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void searchError(Throwable throwable) {
        mDialogManager.dialogMainStyle(throwable.getMessage(),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
    }

    @Override
    public void searchUsersSuccess(List<User> users) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(ARGUMENT_LIST_USER, (ArrayList<? extends Parcelable>) users);
        mNavigator.startActivity(SearchResultActivity.class, bundle);
    }

    @Override
    public void invalidKeyWord(String errorMsg) {
        setKeywordErrorMsg(errorMsg);
        notifyPropertyChanged(BR.keywordErrorMsg);
    }

    @Override
    public void invalidLimitNumber(String errorMsg) {
        setLimitErrorMsg(errorMsg);
        notifyPropertyChanged(BR.limitErrorMsg);
    }

    @Bindable
    public String getKeyWord() {
        return mKeyWord;
    }

    public void setKeyWord(String keyWord) {
        mKeyWord = keyWord;
    }

    @Bindable
    public String getLimit() {
        return mLimit == Integer.MIN_VALUE ? "" : String.valueOf(mLimit);
    }

    public void setLimit(String limit) {
        if (TextUtils.isEmpty(limit)) {
            return;
        }
        mLimit = Integer.parseInt(limit);
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

    public void onSearchButtonClicked(View view) {
        mPresenter.searchUsers(mLimit, mKeyWord);
    }
}
