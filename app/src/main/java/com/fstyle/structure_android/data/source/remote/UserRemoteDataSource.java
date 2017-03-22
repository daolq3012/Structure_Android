package com.fstyle.structure_android.data.source.remote;

import com.fstyle.structure_android.data.model.User;
import com.fstyle.structure_android.data.model.UsersList;
import com.fstyle.structure_android.data.source.UserDataSource;
import com.fstyle.structure_android.data.source.remote.api.service.NameApi;
import java.util.List;
import javax.inject.Inject;
import rx.Observable;
import rx.functions.Func1;

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
    public Observable<List<User>> searchUsers(int limit, String keyWord) {
        return mNameApi.searchGithubUsers(limit, keyWord)
                .flatMap(new Func1<UsersList, Observable<List<User>>>() {
                    @Override
                    public Observable<List<User>> call(UsersList usersList) {
                        if (usersList != null) {
                            return Observable.just(usersList.getItems());
                        }
                        return Observable.error(new NullPointerException());
                    }
                });
    }
}
