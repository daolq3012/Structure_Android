package com.fstyle.structure_android.data.source.remote_api.response;

import com.fstyle.structure_android.data.model.User;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by le.quang.dao on 10/03/2017.
 */

public class SearchUserResponse {

    public SearchUserResponse(List<User> items) {
        this.items = items;
    }

    @SerializedName("total_count")
    @Expose
    private Integer totalCount;

    @SerializedName("items")
    @Expose
    private List<User> items = new ArrayList<>();

    public Integer getTotalCount() {
        return totalCount;
    }

    public List<User> getItems() {
        return items;
    }
}
