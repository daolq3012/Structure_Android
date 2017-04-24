package com.fstyle.structure_android.screen.searchresult;

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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by le.quang.dao on 14/03/2017.
 */

public class SearchResultAdapter
        extends BaseRecyclerViewAdapter<SearchResultAdapter.ItemViewHolder> {

    private List<User> mUsers;

    protected SearchResultAdapter(@NonNull Context context, @NonNull List<User> users) {
        super(context);
        mUsers = new ArrayList<>();
        mUsers.addAll(users);
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutItem = LayoutInflater.from(getContext())
                .inflate(R.layout.layout_item_search_result, parent, false);
        return new ItemViewHolder(layoutItem);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        holder.setData(mUsers.get(position));
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    /**
     * ItemViewHolder
     */
    static class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView mTextViewUserLogin;

        ItemViewHolder(View itemView) {
            super(itemView);
            mTextViewUserLogin = (TextView) itemView.findViewById(R.id.tvUserLogin);
        }

        public void setData(User user) {
            mTextViewUserLogin.setText(user.getLogin());
        }
    }
}
