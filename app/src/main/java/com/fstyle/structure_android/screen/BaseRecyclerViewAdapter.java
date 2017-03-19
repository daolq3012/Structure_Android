package com.fstyle.structure_android.screen;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

/**
 * Created by le.quang.dao on 14/03/2017.
 * Base Adapter.
 *
 * @param <V> is a type extend from {@link RecyclerView.ViewHolder}
 */

public abstract class BaseRecyclerViewAdapter<V extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<V> {

    private final Context mContext;

    protected BaseRecyclerViewAdapter(@NonNull Context context) {
        mContext = context;
    }

    protected Context getContext() {
        return mContext;
    }

    /**
     * OnRecyclerViewItemClickListener
     */
    public interface OnRecyclerViewItemClickListener<T> {
        void onItemRecyclerViewClick(T item);
    }
}
