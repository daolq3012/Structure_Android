package com.fstyle.structure_android.data.source.remote;

import com.fstyle.structure_android.data.model.UsersList;
import com.fstyle.structure_android.data.source.UserDataSource;
import com.fstyle.structure_android.data.source.remote.api.service.NameApi;
import rx.Observable;

/**
 * Created by le.quang.dao on 10/03/2017.
 */

public class UserRemoteDataSource extends BaseRemoteDataSource
        implements UserDataSource.RemoteDataSource {
    public UserRemoteDataSource(NameApi nameApi) {
        super(nameApi);
    }

    @Override
    public Observable<UsersList> searchUsers(int limit, String searchTerm) {
        return mNameApi.searchGithubUsers(limit, searchTerm);
    }
}
