package com.fstyle.structure_android.screen.searchresult;

import android.os.Bundle;
import com.fstyle.structure_android.screen.BaseViewModel;
import com.fstyle.structure_android.screen.userdetail.UserDetailActivity;
import com.fstyle.structure_android.utils.Constant;
import com.fstyle.structure_android.utils.navigator.Navigator;

/**
 * Created by daolq on 1/9/18.
 */

public class SearchResultViewModel extends BaseViewModel
        implements SearchResultAdapter.ItemClickListener {

    private SearchResultAdapter mAdapter;
    private Navigator mNavigator;

    public SearchResultViewModel(SearchResultAdapter adapter, Navigator navigator) {
        mAdapter = adapter;
        mNavigator = navigator;
        mAdapter.setItemClickListener(this);
    }

    @Override
    protected void onStart() {

    }

    @Override
    protected void onStop() {

    }

    @Override
    public void onItemClicked(Integer userId) {
        if (userId == null) {
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putInt(Constant.ARGUMENT_USER_ID, userId);
        mNavigator.startActivity(UserDetailActivity.class, bundle);
    }

    public SearchResultAdapter getAdapter() {
        return mAdapter;
    }
}
