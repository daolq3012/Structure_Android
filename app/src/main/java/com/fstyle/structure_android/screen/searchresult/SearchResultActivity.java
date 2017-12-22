package com.fstyle.structure_android.screen.searchresult;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.fstyle.structure_android.R;
import com.fstyle.structure_android.data.model.User;
import com.fstyle.structure_android.screen.BaseActivity;
import com.fstyle.structure_android.screen.userdetail.UserDetailActivity;
import com.fstyle.structure_android.utils.Constant;
import com.fstyle.structure_android.utils.navigator.Navigator;

import java.util.ArrayList;

/**
 * SearchResult Screen.
 */
public class SearchResultActivity extends BaseActivity implements SearchResultContract.View, SearchResultAdapter.ItemClickListener {

    private SearchResultContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        mPresenter = new SearchResultPresenter();
        mPresenter.setView(this);

        RecyclerView recyclerView = findViewById(R.id.resultRecyclerView);

        ArrayList<User> users = getIntent().getParcelableArrayListExtra(Constant.LIST_USER_ARGS);
        SearchResultAdapter searchResultAdapter = new SearchResultAdapter(getApplicationContext(), users);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(),DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(searchResultAdapter);
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
    public void onItemClicked(int userId) {
        Navigator navigator = new Navigator(this);
        navigator.startActivity(UserDetailActivity.class);
    }
}
