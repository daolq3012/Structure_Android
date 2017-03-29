package com.fstyle.structure_android.utils.string;

import android.text.TextUtils;

/**
 * Created by le.quang.dao on 28/03/2017.
 */

public final class StringUtils {
    private StringUtils() {
        // No-op
    }

    public static boolean isBlank(String str) {
        return str == null || TextUtils.isEmpty(str.trim()) || str.equalsIgnoreCase("null");
    }
}
