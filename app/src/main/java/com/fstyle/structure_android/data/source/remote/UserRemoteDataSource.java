package com.fstyle.structure_android.data.source.remote;

import com.fstyle.structure_android.data.model.User;
import com.fstyle.structure_android.data.model.UsersList;
import com.fstyle.structure_android.data.source.UserDataSource;
import com.fstyle.structure_android.data.source.remote.api.service.NameApi;
import java.util.List;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by le.quang.dao on 10/03/2017.
 */

public class UserRemoteDataSource extends BaseRemoteDataSource
        implements UserDataSource.RemoteDataSource {
    public UserRemoteDataSource(NameApi nameApi) {
        super(nameApi);
    }

    @Override
    public Observable<List<User>> searchUsers(String keyWord, int limit) {
        return mNameApi.searchGithubUsers(limit, keyWord).map(new Func1<UsersList, List<User>>() {
            @Override
            public List<User> call(UsersList usersList) {
                return usersList.getItems();
            }
        });
    }
}
