package com.fstyle.structure_android.screen.userlist;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.fstyle.structure_android.R;
import com.fstyle.structure_android.data.model.User;
import com.fstyle.structure_android.screen.BaseRecyclerViewAdapter;
import com.google.common.base.Preconditions;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by le.quang.dao on 14/03/2017.
 */

public class UserListAdapter extends BaseRecyclerViewAdapter<UserListAdapter.ItemViewHolder> {

    private List<User> mUsers = new ArrayList<>();
    private ItemClickListener mItemClickListener;

    UserListAdapter(@NonNull Context context) {
        super(context);
        mItemClickListener = (ItemClickListener) Preconditions.checkNotNull(context);
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutItem = LayoutInflater.from(getContext())
                .inflate(R.layout.layout_item_search_result, parent, false);
        return new ItemViewHolder(layoutItem, mItemClickListener);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        holder.setData(mUsers.get(position));
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    void replaceData(@NonNull List<User> users) {
        mUsers.clear();
        mUsers.addAll(users);
        notifyDataSetChanged();
    }

    /**
     * ItemViewHolder
     */
    static class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView mTextViewUserLogin;

        private User mUser;

        ItemViewHolder(View itemView, final ItemClickListener mItemClickListener) {
            super(itemView);
            mTextViewUserLogin = itemView.findViewById(R.id.tvUserLogin);

            itemView.setOnClickListener(view -> {
                if (mUser.getAvatarUrl() == null) {
                    return;
                }
                mItemClickListener.onItemClicked(mUser.getAvatarUrl());
            });
        }

        public void setData(User user) {
            mUser = user;
            mTextViewUserLogin.setText(user.getLogin());
        }
    }

    /**
     * ItemClickListener
     */
    public interface ItemClickListener {
        void onItemClicked(String userLogin);
    }
}
