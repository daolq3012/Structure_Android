package com.fstyle.structure_android.screen.main;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.EditText;
import com.fstyle.library.DialogAction;
import com.fstyle.library.MaterialDialog;
import com.fstyle.structure_android.R;
import com.fstyle.structure_android.data.model.User;
import com.fstyle.structure_android.data.source.UserRepository;
import com.fstyle.structure_android.data.source.UserRepositoryImpl;
import com.fstyle.structure_android.data.source.remote.UserRemoteDataSource;
import com.fstyle.structure_android.data.source.remote.api.error.BaseException;
import com.fstyle.structure_android.data.source.remote.api.service.NameServiceClient;
import com.fstyle.structure_android.screen.BaseActivity;
import com.fstyle.structure_android.screen.searchresult.SearchResultActivity;
import com.fstyle.structure_android.utils.navigator.Navigator;
import com.fstyle.structure_android.utils.rx.SchedulerProvider;
import com.fstyle.structure_android.widget.dialog.DialogManager;
import com.fstyle.structure_android.widget.dialog.DialogManagerImpl;
import java.util.ArrayList;
import java.util.List;

import static com.fstyle.structure_android.utils.Constant.LIST_USER_ARGS;

/**
 * Create by DaoLQ
 */
public class MainActivity extends BaseActivity implements MainContract.View {

    private MainContract.Presenter mPresenter;

    private TextInputLayout mTextInputLayoutKeyword;
    private EditText mEditTextKeyword;
    private TextInputLayout mTextInputLayoutNumberLimit;
    private EditText mEditNumberLimit;

    private DialogManager mDialogManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UserRepository userRepository = new UserRepositoryImpl(null,
                new UserRemoteDataSource(NameServiceClient.getInstance()));
        mPresenter = new MainPresenter(userRepository);
        mPresenter.setView(this);
        mPresenter.setSchedulerProvider(SchedulerProvider.getInstance());

        mTextInputLayoutKeyword = (TextInputLayout) findViewById(R.id.txtInputLayoutKeyword);
        mEditTextKeyword = (EditText) findViewById(R.id.edtKeyword);
        mTextInputLayoutNumberLimit =
                (TextInputLayout) findViewById(R.id.txtInputLayoutNumberLimit);
        mEditNumberLimit = (EditText) findViewById(R.id.edtNumberLimit);

        mDialogManager = new DialogManagerImpl(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.onStart();
    }

    @Override
    protected void onStop() {
        mPresenter.onStop();
        super.onStop();
    }

    @Override
    public String getKeyword() {
        return mEditTextKeyword.getText().toString().trim();
    }

    @Override
    public String getLimitNumber() {
        return mEditNumberLimit.getText().toString().trim();
    }

    @Override
    public void onInvalidKeyWord(String errorMsg) {
        mTextInputLayoutKeyword.setError(errorMsg);
    }

    @Override
    public void onInvalidLimitNumber(String errorMsg) {
        mTextInputLayoutNumberLimit.setError(errorMsg);
    }

    @Override
    public void onDataValid() {
        mDialogManager.showIndeterminateProgressDialog();
        mPresenter.searchUsers();
    }


    @Override
    public void onSearchError(BaseException e) {
        mDialogManager.dismissProgressDialog();
        mDialogManager.dialogError(e.getMessage(),
                new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog materialDialog,
                                        @NonNull DialogAction dialogAction) {
                        onSearchButtonClicked(null);
                    }
                });
    }

    @Override
    public void onSearchUsersSuccess(List<User> users) {
        mDialogManager.dismissProgressDialog();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(LIST_USER_ARGS, (ArrayList<? extends Parcelable>) users);
        new Navigator(this).startActivity(SearchResultActivity.class, bundle);
    }

    public void onSearchButtonClicked(View view) {
        mPresenter.validateDataInput();
    }
}
