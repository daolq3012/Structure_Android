package com.fstyle.structure_android.screen.main;

import android.databinding.Observable;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.view.View;
import com.fstyle.library.DialogAction;
import com.fstyle.library.MaterialDialog;
import com.fstyle.structure_android.data.model.User;
import com.fstyle.structure_android.data.repository.UserRepository;
import com.fstyle.structure_android.data.source.remote.config.error.BaseException;
import com.fstyle.structure_android.screen.BaseViewModel;
import com.fstyle.structure_android.screen.searchresult.SearchResultActivity;
import com.fstyle.structure_android.utils.Constant;
import com.fstyle.structure_android.utils.common.StringUtils;
import com.fstyle.structure_android.utils.navigator.Navigator;
import com.fstyle.structure_android.utils.rx.BaseSchedulerProvider;
import com.fstyle.structure_android.widget.dialog.DialogManager;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sun on 3/19/2017.
 * Relative @{@link MainActivity}
 */

public class MainViewModel extends BaseViewModel {

    private UserRepository mUserRepository;
    private DialogManager mDialogManager;
    private Navigator mNavigator;
    private BaseSchedulerProvider mSchedulerProvider;

    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    public ObservableField<String> keyWordStringObservableField = new ObservableField<>();
    public ObservableField<String> limitStringObservableField = new ObservableField<>();
    public ObservableField<String> keywordErrorStringObservableField = new ObservableField<>();
    public ObservableField<String> limitErrorStringObservableField = new ObservableField<>();

    private Observable.OnPropertyChangedCallback propertyChangedCallback =
            new Observable.OnPropertyChangedCallback() {
                @Override
                public void onPropertyChanged(Observable observable, int i) {
                    if (observable == keyWordStringObservableField) {
                        validateKeywordInput();
                    } else if (observable == limitStringObservableField) {
                        validateLimitNumberInput();
                    }
                }
            };

    public MainViewModel(UserRepository mUserRepository, DialogManager dialogManager,
            Navigator navigator) {
        this.mUserRepository = mUserRepository;
        this.mDialogManager = dialogManager;
        this.mNavigator = navigator;
    }

    public void setSchedulerProvider(BaseSchedulerProvider schedulerProvider) {
        mSchedulerProvider = schedulerProvider;
    }

    @Override
    protected void onStart() {
        keyWordStringObservableField.addOnPropertyChangedCallback(propertyChangedCallback);
        limitStringObservableField.addOnPropertyChangedCallback(propertyChangedCallback);
    }

    @Override
    protected void onStop() {
        keyWordStringObservableField.removeOnPropertyChangedCallback(propertyChangedCallback);
        limitStringObservableField.removeOnPropertyChangedCallback(propertyChangedCallback);
        mCompositeDisposable.clear();
    }

    public void onSearchButtonClicked(View view) {
        boolean isValid = validateKeywordInput() & validateLimitNumberInput();
        if (isValid) {
            callAPISearchUsers();
        }
    }

    private void callAPISearchUsers() {
        mDialogManager.showIndeterminateProgressDialog();
        Disposable disposable = mUserRepository.getAllUser()
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(new Consumer<List<User>>() {
                    @Override
                    public void accept(List<User> users) throws Exception {
                        mDialogManager.dismissProgressDialog();
                        gotoSearchResultActivity(users);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        showDialogError((BaseException) throwable);
                        mDialogManager.dismissProgressDialog();
                    }
                });
        mCompositeDisposable.add(disposable);
    }

    private void showDialogError(BaseException e) {
        mDialogManager.dialogError(e.getMessage(), new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog materialDialog,
                    @NonNull DialogAction dialogAction) {
                callAPISearchUsers();
            }
        });
    }

    private void gotoSearchResultActivity(List<User> users) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(Constant.ARGUMENT_LIST_USER,
                (ArrayList<? extends Parcelable>) users);
        mNavigator.startActivity(SearchResultActivity.class, bundle);
    }

    private boolean validateKeywordInput() {
        String keyword = this.keyWordStringObservableField.get();
        if (StringUtils.isBlank(keyword)) {
            keywordErrorStringObservableField.set("Keyword must not empty!");
        }
        return !StringUtils.isBlank(keyword);
    }

    private boolean validateLimitNumberInput() {
        String limit = this.limitStringObservableField.get();
        if (StringUtils.isBlank(limit)) {
            limitErrorStringObservableField.set("Limit number must not empty!");
        }
        return !StringUtils.isBlank(limit);
    }
}
