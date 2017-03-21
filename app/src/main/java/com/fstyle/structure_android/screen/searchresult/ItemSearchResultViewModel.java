package com.fstyle.structure_android.screen.searchresult;

import android.databinding.BaseObservable;
import android.view.View;
import com.fstyle.structure_android.data.model.User;
import com.fstyle.structure_android.screen.BaseRecyclerViewAdapter;

/**
 * Created by le.quang.dao on 20/03/2017.
 */

public class ItemSearchResultViewModel extends BaseObservable {

    private User mUser;
    private BaseRecyclerViewAdapter.OnRecyclerViewItemClickListener<User> mItemClickListener;

    public ItemSearchResultViewModel(User user,
            BaseRecyclerViewAdapter.OnRecyclerViewItemClickListener<User> listener) {
        mUser = user;
        mItemClickListener = listener;
    }

    public String getUserLogin() {
        return mUser.getLogin();
    }

    public void onItemClicked(View view) {
        if (mItemClickListener == null) {
            return;
        }
        mItemClickListener.onItemRecyclerViewClick(mUser);
    }
}
