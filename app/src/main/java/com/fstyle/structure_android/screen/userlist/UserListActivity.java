package com.fstyle.structure_android.screen.userlist;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import com.fstyle.structure_android.R;
import com.fstyle.structure_android.data.model.User;
import com.fstyle.structure_android.data.repository.UserRepository;
import com.fstyle.structure_android.data.source.local.UserLocalDataSource;
import com.fstyle.structure_android.data.source.remote.UserRemoteDataSource;
import com.fstyle.structure_android.screen.BaseActivity;
import com.fstyle.structure_android.screen.userdetail.UserDetailActivity;
import com.fstyle.structure_android.utils.Constant;
import com.fstyle.structure_android.utils.navigator.Navigator;
import com.fstyle.structure_android.utils.rx.SchedulerProvider;
import com.fstyle.structure_android.widget.dialog.DialogManager;
import com.fstyle.structure_android.widget.dialog.DialogManagerImpl;
import java.util.List;

/**
 * User List Screen.
 */
public class UserListActivity extends BaseActivity
        implements UserListContract.View, UserListAdapter.ItemClickListener {

    private UserListContract.Presenter mPresenter;
    private DialogManager mDialogManager;

    private UserListAdapter mUserListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        UserRepository userRepository =
                UserRepository.getInstance(UserLocalDataSource.getInstance(this),
                        UserRemoteDataSource.getInstance(this));
        mPresenter = new UserListPresenter(userRepository, SchedulerProvider.getInstance());
        mPresenter.setView(this);
        mDialogManager = new DialogManagerImpl(this);

        RecyclerView recyclerView = findViewById(R.id.resultRecyclerView);

        mUserListAdapter = new UserListAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.addItemDecoration(
                new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(mUserListAdapter);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_user_list, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showLoadingIndicator() {
        mDialogManager.showIndeterminateProgressDialog();
    }

    @Override
    public void hideLoadingIndicator() {
        mDialogManager.dismissProgressDialog();
    }

    @Override
    public void showLoadingUsersError(Throwable throwable) {
        mDialogManager.dialogError(throwable.getMessage());
    }

    @Override
    public void showUsers(List<User> users) {
        mUserListAdapter.replaceData(users);
    }

    @Override
    public void showNoUser() {

    }

    @Override
    public void onItemClicked(String avatarUrl) {
        Navigator navigator = new Navigator(this);
        Bundle bundle = new Bundle();
        bundle.putString(Constant.ARGS_AVATAR_URL, avatarUrl);
        navigator.startActivity(UserDetailActivity.class, bundle);
    }
}
