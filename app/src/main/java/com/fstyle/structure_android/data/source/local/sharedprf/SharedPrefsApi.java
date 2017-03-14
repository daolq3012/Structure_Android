package com.fstyle.structure_android.data.source.local.sharedprf;

/**
 * Created by le.quang.dao on 14/03/2017.
 */

public interface SharedPrefsApi {
    <T> T get(String key, Class<T> clazz);

    <T> void put(String key, T data);

    void clear();
}
