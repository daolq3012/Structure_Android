package com.fstyle.structure_android.data.source.remote.config.service;

import android.content.Context;
import android.support.annotation.NonNull;
import com.fstyle.structure_android.utils.Constant;

/**
 * Created by le.quang.dao on 10/03/2017.
 */

public class AppServiceClient extends ServiceClient {

    private static NameApi mNameApiInstance;

    public static NameApi getNameApiInstance(@NonNull Context context) {
        if (mNameApiInstance == null) {
            mNameApiInstance = createService(context, Constant.END_POINT_URL, NameApi.class);
        }
        return mNameApiInstance;
    }
}
