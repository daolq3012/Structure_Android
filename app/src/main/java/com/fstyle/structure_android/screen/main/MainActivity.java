package com.fstyle.structure_android.screen.main;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import com.fstyle.structure_android.R;
import com.fstyle.structure_android.data.model.User;
import com.fstyle.structure_android.data.source.UserRepositoryImpl;
import com.fstyle.structure_android.data.source.remote.UserRemoteDataSource;
import com.fstyle.structure_android.data.source.remote.api.service.NameServiceClient;
import com.fstyle.structure_android.screen.BaseActivity;
import com.fstyle.structure_android.screen.searchresult.SearchResultActivity;
import com.fstyle.structure_android.utils.common.SimpleTextWatcher;
import com.fstyle.structure_android.utils.navigator.Navigator;
import com.fstyle.structure_android.utils.rx.SchedulerProvider;
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
    @Validation({
            @Rule(types = ValidType.NG_WORD, message = R.string.error_unusable_characters),
            @Rule(types = ValidType.NON_EMPTY, message = R.string.must_not_empty)
    })
    private EditText mEditTextKeyword;
    private TextInputLayout mTextInputLayoutNumberLimit;
    @Validation({
            @Rule(types = ValidType.VALUE_RANGE_0_100, message = R.string
                    .error_lenght_from_0_to_100),
            @Rule(types = ValidType.NON_EMPTY, message = R.string.must_not_empty)
    })
    private EditText mEditNumberLimit;

    private DialogManager mDialogManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UserRepositoryImpl userRepository = new UserRepositoryImpl(null,
                new UserRemoteDataSource(NameServiceClient.getInstance()));
        Validator validator = new Validator(getApplicationContext(), this);
        mPresenter = new MainPresenter(userRepository, validator);
        mPresenter.setView(this);
        mPresenter.setSchedulerProvider(SchedulerProvider.getInstance());

        mTextInputLayoutKeyword = (TextInputLayout) findViewById(R.id.txtInputLayoutKeyword);
        mEditTextKeyword = (EditText) findViewById(R.id.edtKeyword);
        mEditTextKeyword.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                mPresenter.validateKeywordInput(s.toString());
            }
        });
        mTextInputLayoutNumberLimit =
                (TextInputLayout) findViewById(R.id.txtInputLayoutNumberLimit);
        mEditNumberLimit = (EditText) findViewById(R.id.edtNumberLimit);
        mEditNumberLimit.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                mPresenter.validateLimitNumberInput(s.toString());
            }
        });

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
    public void onSearchError(Throwable throwable) {
//        mDialogManager.dismissProgressDialog();
//        mDialogManager.dialogError(throwable.getMessage(),
//                new MaterialDialog.SingleButtonCallback() {
//                    @Override
//                    public void onClick(@NonNull MaterialDialog materialDialog,
//                            @NonNull DialogAction dialogAction) {
//                        onSearchButtonClicked(null);
//                    }
//                });
    }

    @Override
    public void onSearchUsersSuccess(List<User> users) {
//        mDialogManager.dismissProgressDialog();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(LIST_USER_ARGS, (ArrayList<? extends Parcelable>) users);
        new Navigator(this).startActivity(SearchResultActivity.class, bundle);
    }

    @Override
    public void onInvalidKeyWord(String errorMsg) {
        mTextInputLayoutKeyword.setError(errorMsg);
    }

    @Override
    public void onInvalidLimitNumber(String errorMsg) {
        mTextInputLayoutNumberLimit.setError(errorMsg);
    }

    public void onSearchButtonClicked(View view) {
        String keyWord = mEditTextKeyword.getText().toString().trim();
        String limit = mEditNumberLimit.getText().toString().trim();

        if (!mPresenter.validateDataInput(keyWord, limit)) {
            return;
        }
//        mDialogManager.showIndeterminateProgressDialog();
        mPresenter.searchUsers(Integer.parseInt(limit), keyWord);
    }
}
