package com.fstyle.structure_android.screen.searchresult;

import android.databinding.BaseObservable;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.view.View;
import com.fstyle.structure_android.data.model.User;

/**
 * Created by daolq on 1/9/18.
 */

public class ItemSearchResultViewModel extends BaseObservable {

    public ObservableField<User> userObservableField = new ObservableField<>();
    private SearchResultAdapter.ItemClickListener mItemClickListener;

    public ItemSearchResultViewModel(SearchResultAdapter.ItemClickListener listener) {
        mItemClickListener = listener;
    }

    public void setUser(@NonNull User user) {
        userObservableField.set(user);
    }

    public void onItemClicked(View view) {
        if (mItemClickListener == null || userObservableField.get() == null) {
            return;
        }
        mItemClickListener.onItemClicked(userObservableField.get().getId());
    }
}
