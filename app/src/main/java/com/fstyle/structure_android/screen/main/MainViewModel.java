package com.fstyle.structure_android.screen.main;

import android.content.DialogInterface;
import android.databinding.Bindable;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.View;
import com.fstyle.structure_android.BR;
import com.fstyle.structure_android.R;
import com.fstyle.structure_android.data.model.User;
import com.fstyle.structure_android.data.model.UsersList;
import com.fstyle.structure_android.data.source.UserRepository;
import com.fstyle.structure_android.screen.BaseViewModel;
import com.fstyle.structure_android.screen.searchresult.SearchResultActivity;
import com.fstyle.structure_android.utils.Constant;
import com.fstyle.structure_android.utils.navigator.Navigator;
import com.fstyle.structure_android.utils.validator.Rule;
import com.fstyle.structure_android.utils.validator.ValidType;
import com.fstyle.structure_android.utils.validator.Validation;
import com.fstyle.structure_android.utils.validator.Validator;
import com.fstyle.structure_android.widget.dialog.DialogManager;
import java.util.ArrayList;
import java.util.List;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static com.fstyle.structure_android.utils.Constant.ARGUMENT_LIST_USER;

/**
 * Created by Sun on 3/19/2017.
 */

public class MainViewModel extends BaseViewModel {
    private Validator mValidator;
    private UserRepository mUserRepository;
    private DialogManager mDialogManager;
    private Navigator mNavigator;

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

    public MainViewModel(UserRepository mUserRepository, DialogManager dialogManager,
            Navigator navigator) {
        this.mUserRepository = mUserRepository;
        this.mDialogManager = dialogManager;
        this.mNavigator = navigator;
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
        if (!validateDataInput(mKeyWord, mLimit)) {
            return;
        }
        Subscription subscription = mUserRepository.getRemoteDataSource()
                .searchUsers(mKeyWord, mLimit)
                .subscribeOn(Schedulers.io())
                .flatMap(new Func1<UsersList, Observable<List<User>>>() {
                    @Override
                    public Observable<List<User>> call(UsersList usersList) {
                        return Observable.just(usersList.getItems());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<User>>() {
                    @Override
                    public void call(List<User> users) {
                        Bundle bundle = new Bundle();
                        bundle.putParcelableArrayList(ARGUMENT_LIST_USER,
                                (ArrayList<? extends Parcelable>) users);
                        mNavigator.startActivity(SearchResultActivity.class, bundle);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mDialogManager.dialogMainStyle(throwable.getMessage(),
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                    }
                });
        startSubscriber(subscription);
    }

    private boolean validateDataInput(String keyWord, int limit) {
        String errorMsg = mValidator.validateNGWord(keyWord);
        errorMsg += (TextUtils.isEmpty(errorMsg) ? "" : Constant.BREAK_LINE)
                + mValidator.validateValueNonEmpty(keyWord);
        mKeywordErrorMsg = TextUtils.isEmpty(errorMsg) ? null : errorMsg;
        notifyPropertyChanged(BR.keywordErrorMsg);

        errorMsg = mValidator.validateValueRangeFrom0to100(limit);
        mLimitErrorMsg = TextUtils.isEmpty(errorMsg) ? null : errorMsg;
        notifyPropertyChanged(BR.limitErrorMsg);

        return mValidator.validateAll(this, false);
    }
}
