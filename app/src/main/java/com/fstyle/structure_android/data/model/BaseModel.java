package com.fstyle.structure_android.data.model;

import com.google.gson.Gson;

/**
 * Created by le.quang.dao on 16/03/2017.
 */

public abstract class BaseModel implements Cloneable {

    public BaseModel clone() throws CloneNotSupportedException {
        Gson gson = new Gson();
        return gson.fromJson(gson.toJson(this), this.getClass());
    }
}
