package com.fstyle.structure_android.screen.main;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.fstyle.structure_android.R;
import com.fstyle.structure_android.data.model.User;
import com.fstyle.structure_android.data.source.UserRepository;
import com.fstyle.structure_android.data.source.remote.UserRemoteDataSource;
import com.fstyle.structure_android.data.source.remote.api.service.NameServiceClient;
import com.fstyle.structure_android.screen.BaseActivity;
import com.fstyle.structure_android.screen.searchresult.SearchResultActivity;
import com.fstyle.structure_android.utils.navigator.NavigatorImpl;
import com.fstyle.structure_android.utils.validator.Rule;
import com.fstyle.structure_android.utils.validator.ValidType;
import com.fstyle.structure_android.utils.validator.Validation;
import com.fstyle.structure_android.utils.validator.Validator;
import com.fstyle.structure_android.widget.dialog.DialogManager;
import com.fstyle.structure_android.widget.dialog.DialogManagerImpl;

import java.util.ArrayList;
import java.util.List;

import static com.fstyle.structure_android.utils.Constant.LIST_USER_ARGS;

/**
 * Create by DaoLQ
 */
public class MainActivity extends BaseActivity implements MainContract.View {
    private static final String TAG = MainActivity.class.getName();

    private MainContract.Presenter mPresenter;

    private TextInputLayout mTextInputLayoutKeyword;
    private EditText mEditTextKeyword;
    private TextInputLayout mTextInputLayoutNumberLimit;
    private EditText mEditNumberLimit;

    private DialogManager mDialogManager;

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
    private int mLimit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UserRepository userRepository =
                new UserRepository(null, new UserRemoteDataSource(NameServiceClient.getInstance()));
        Validator validator = new Validator(getApplicationContext(), this);
        mPresenter = new MainPresenter(this, userRepository, validator);

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
    public void showError(Throwable throwable) {
        mDialogManager.dialogMainStyle(throwable.getMessage(),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
    }

    @Override
    public void showListUser(List<User> users) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(LIST_USER_ARGS, (ArrayList<? extends Parcelable>) users);
        new NavigatorImpl(this).startActivity(SearchResultActivity.class, bundle);
    }

    @Override
    public void showInvalidLimit(String errorMsg) {
        mTextInputLayoutNumberLimit.setError(errorMsg);
    }

    @Override
    public void showInvalidUserName(String errorMsg) {
        mTextInputLayoutKeyword.setError(errorMsg);
    }

    public void onSearchButtonClicked(View view) {
        mKeyWord = mEditTextKeyword.getText().toString().trim();
        String limitStr = mEditNumberLimit.getText().toString().trim();
        mLimit = TextUtils.isEmpty(limitStr) ? Integer.MIN_VALUE : Integer.parseInt(limitStr);
        mPresenter.searchUsers(mLimit, mKeyWord);
    }
}
