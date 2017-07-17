package com.fstyle.structure_android.data.source.remote;

import com.fstyle.structure_android.data.model.User;
import com.fstyle.structure_android.data.model.UsersList;
import com.fstyle.structure_android.data.source.UserDataSource;
import com.fstyle.structure_android.data.source.remote.api.service.NameApi;
import io.reactivex.Single;
import io.reactivex.functions.Function;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by le.quang.dao on 10/03/2017.
 */

public class UserRemoteDataSource extends BaseRemoteDataSource
        implements UserDataSource.RemoteDataSource {

    @Inject
    public UserRemoteDataSource(NameApi nameApi) {
        super(nameApi);
    }

    @Override
    public Single<List<User>> searchUsers(String keyWord, int limit) {
        return getNameApi().searchGithubUsers(limit, keyWord)
                .map(new Function<UsersList, List<User>>() {
                    @Override
                    public List<User> apply(UsersList usersList) throws Exception {
                        return usersList.getItems();
                    }
                });
    }
}
