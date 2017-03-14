package com.fstyle.structure_android.screen.searchresult;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.fstyle.structure_android.R;
import com.fstyle.structure_android.data.model.User;
import com.fstyle.structure_android.screen.BaseActivity;
import com.fstyle.structure_android.utils.Constant;
import java.util.ArrayList;

/**
 * SearchResult Screen.
 */
public class SearchResultActivity extends BaseActivity implements SearchResultContract.View {

    private SearchResultContract.Presenter mPresenter;

    private RecyclerView mRecyclerView;

    private SearchResultAdapter mSearchResultAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchresult);

        new SearchResultPresenter(this);

        mRecyclerView = (RecyclerView) findViewById(R.id.resultRecyclerView);

        ArrayList<User> users = getIntent().getParcelableArrayListExtra(Constant.LIST_USER_ARGS);
        mSearchResultAdapter = new SearchResultAdapter(this, users);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mSearchResultAdapter);
    }

    @Override
    public void setPresenter(SearchResultContract.Presenter presenter) {
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
}
