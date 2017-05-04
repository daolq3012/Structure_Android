package com.fstyle.structure_android.screen.searchresult;

import com.fstyle.structure_android.data.model.User;
import com.fstyle.structure_android.screen.BaseRecyclerViewAdapter;
import com.fstyle.structure_android.screen.BaseViewModel;
import com.fstyle.structure_android.utils.rx.BaseSchedulerProvider;

/**
 * Created by le.quang.dao on 20/03/2017.
 */

public class SearchResultViewModel extends BaseViewModel
        implements BaseRecyclerViewAdapter.OnRecyclerViewItemClickListener<User> {

    private SearchResultAdapter mAdapter;

    public SearchResultViewModel(SearchResultAdapter adapter) {
        mAdapter = adapter;
        mAdapter.setItemClickListener(this);
    }

    @Override
    public void onItemRecyclerViewClick(User item) {
    }

    public SearchResultAdapter getAdapter() {
        return mAdapter;
    }

    @Override
    public void setSchedulerProvider(BaseSchedulerProvider schedulerProvider) {

    }
}
