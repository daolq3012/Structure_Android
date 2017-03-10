package com.fstyle.structure_android.data.source.remote.api.service;

import android.app.Application;
import android.support.annotation.NonNull;
import com.fstyle.structure_android.utils.Constant;

/**
 * Created by le.quang.dao on 10/03/2017.
 */

public class NameServiceClient extends ServiceClient {

    private static NameApi mNameApiInstance;

    public static void initialize(@NonNull Application application) {
        mNameApiInstance = createService(application, Constant.END_POINT_URL, NameApi.class);
    }

    public static NameApi getInstance() {
        if (mNameApiInstance == null) {
            throw new RuntimeException("Need call method NameServiceClient#initialize() first");
        }
        return mNameApiInstance;
    }
}
