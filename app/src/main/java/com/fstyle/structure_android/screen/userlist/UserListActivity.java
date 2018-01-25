package com.fstyle.structure_android.screen.userlist;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.fstyle.structure_android.R;
import com.fstyle.structure_android.data.model.User;
import com.fstyle.structure_android.data.repository.UserRepository;
import com.fstyle.structure_android.data.source.local.UserLocalDataSource;
import com.fstyle.structure_android.data.source.remote.UserRemoteDataSource;
import com.fstyle.structure_android.screen.BaseActivity;
import com.fstyle.structure_android.screen.userdetail.UserDetailActivity;
import com.fstyle.structure_android.utils.Constant;
import com.fstyle.structure_android.utils.common.StringUtils;
import com.fstyle.structure_android.utils.navigator.Navigator;
import com.fstyle.structure_android.utils.rx.SchedulerProvider;
import com.fstyle.structure_android.widget.dialog.DialogManager;
import com.fstyle.structure_android.widget.dialog.DialogManagerImpl;
import java.util.List;

/**
 * User List Screen.
 */
public class UserListActivity extends BaseActivity
        implements UserListContract.View, UserListAdapter.ItemClickListener,
        SwipeRefreshLayout.OnRefreshListener, SearchView.OnQueryTextListener {

    private UserListContract.Presenter mPresenter;
    private DialogManager mDialogManager;

    private UserListAdapter mUserListAdapter;

    private SwipeRefreshLayout mRefreshLayout;

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

        mRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        mRefreshLayout.setOnRefreshListener(this);
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
        initSearchView(menu);
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
        mRefreshLayout.setVisibility(View.VISIBLE);
        mUserListAdapter.replaceData(users);
    }

    @Override
    public void showNoUser() {
        mRefreshLayout.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onItemClicked(String avatarUrl) {
        Navigator navigator = new Navigator(this);
        Bundle bundle = new Bundle();
        bundle.putString(Constant.ARGS_AVATAR_URL, avatarUrl);
        navigator.startActivity(UserDetailActivity.class, bundle);
    }

    private void initSearchView(Menu menu) {
        MenuItem searchItem = menu.findItem(R.id.action_search);
        if (searchItem != null) {
            SearchView searchView = (SearchView) searchItem.getActionView();
            searchView.setInputType(InputType.TYPE_TEXT_VARIATION_FILTER);
            searchView.setQueryHint(getString(R.string.input_content_need_search));
            searchView.setOnQueryTextListener(this);

            // Handle when searchView closed
            searchItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
                @Override
                public boolean onMenuItemActionExpand(MenuItem menuItem) {
                    mRefreshLayout.setEnabled(false);
                    return true;
                }

                @Override
                public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                    mRefreshLayout.setEnabled(true);
                    return true;
                }
            });
        }
    }

    @Override
    public void onRefresh() {
        mRefreshLayout.setRefreshing(false);
        mPresenter.loadUsers(true);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (StringUtils.isNotBlank(newText)) {
            mPresenter.searchUsers(newText);
        } else {
            mPresenter.loadUsers(false);
        }
        return false;
    }
}
