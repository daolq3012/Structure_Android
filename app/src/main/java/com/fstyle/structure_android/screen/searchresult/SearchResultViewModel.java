package com.fstyle.structure_android.screen.searchresult;

import android.databinding.BaseObservable;
import com.fstyle.structure_android.data.model.User;
import com.fstyle.structure_android.screen.BaseRecyclerViewAdapter;

/**
 * Created by le.quang.dao on 21/03/2017.
 */

public class SearchResultViewModel extends BaseObservable implements SearchResultContract.ViewModel,
        BaseRecyclerViewAdapter.OnRecyclerViewItemClickListener<User> {

    private SearchResultContract.Presenter mPresenter;

    private SearchResultAdapter mAdapter;

    public SearchResultViewModel(SearchResultContract.Presenter presenter,
            SearchResultAdapter adapter) {
        mPresenter = presenter;
        mPresenter.setViewModel(this);
        mAdapter = adapter;
        mAdapter.setItemClickListener(this);
    }

    public SearchResultAdapter getAdapter() {
        return mAdapter;
    }

    @Override
    public void onStart() {
        mPresenter.onStart();
    }

    @Override
    public void onStop() {
        mPresenter.onStop();
    }

    @Override
    public void onItemRecyclerViewClick(User user) {
        mPresenter.onItemUserClicked(user);
    }
}
