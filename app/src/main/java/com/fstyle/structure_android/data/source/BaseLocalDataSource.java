package com.fstyle.structure_android.data.source;

/**
 * Created by le.quang.dao on 13/03/2017.
 */

public interface BaseLocalDataSource {
    void openTransaction();

    void closeTransaction();

    void openReadTransaction();
}
