package com.fstyle.structure_android.data.source;

import com.fstyle.structure_android.data.model.UsersList;

import rx.Observable;

/**
 * Created by le.quang.dao on 10/03/2017.
 */

public interface UserDataSource {
    /**
     * LocalData For User
     */
    interface LocalDataSource {

    }

    /**
     * RemoteData For User
     */
    interface RemoteDataSource {
        Observable<UsersList> searchUsers(String searchTerm);
    }
}
