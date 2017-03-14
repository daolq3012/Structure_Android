package com.fstyle.structure_android.screen.main;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import com.fstyle.structure_android.R;
import com.fstyle.structure_android.data.model.UsersList;
import com.fstyle.structure_android.data.source.UserRepository;
import com.fstyle.structure_android.data.source.remote.UserRemoteDataSource;
import com.fstyle.structure_android.data.source.remote.api.service.NameServiceClient;
import com.fstyle.structure_android.screen.BaseActivity;
import com.fstyle.structure_android.screen.searchresult.SearchResultActivity;
import com.fstyle.structure_android.utils.Navigator;
import com.fstyle.structure_android.widget.dialog.DialogManager;
import com.fstyle.structure_android.widget.dialog.DialogManagerImpl;
import java.util.ArrayList;

import static com.fstyle.structure_android.utils.Constant.LIST_USER_ARGS;

/**
 * Create by DaoLQ
 */
public class MainActivity extends BaseActivity implements MainContract.View {
    private static final String TAG = MainActivity.class.getName();

    private MainContract.Presenter mPresenter;

    private EditText mEditTextSearch;
    private EditText mEditNumberLimit;

    private DialogManager mDialogManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UserRepository mUserRepository =
                new UserRepository(null, new UserRemoteDataSource(NameServiceClient.getInstance()));
        new MainPresenter(this, mUserRepository);

        mEditTextSearch = (EditText) findViewById(R.id.edtSearch);
        mEditNumberLimit = (EditText) findViewById(R.id.edtNumberLimit);

        mDialogManager = new DialogManagerImpl(this);
    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {
        mPresenter = presenter;
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
    public void showListUser(UsersList usersList) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(LIST_USER_ARGS,
                (ArrayList<? extends Parcelable>) usersList.getItems());
        new Navigator(this).startActivity(SearchResultActivity.class, bundle);
    }

    public void onSearchButtonClicked(View view) {
        String keyword = mEditTextSearch.getText().toString().trim();
        String limitStr = mEditNumberLimit.getText().toString().trim();
        int limit = TextUtils.isEmpty(limitStr) ? 0 : Integer.parseInt(limitStr);
        if (!TextUtils.isEmpty(keyword)) {
            mPresenter.searchUsers(limit, keyword);
        }
    }
}
