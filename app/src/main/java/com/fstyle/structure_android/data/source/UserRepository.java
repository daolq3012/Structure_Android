package com.fstyle.structure_android.data.source;

import com.fstyle.structure_android.data.model.User;
import java.util.List;
import rx.Observable;

/**
 * Created by le.quang.dao on 10/03/2017.
 */

public class UserRepository {

    private UserDataSource.LocalDataSource mLocalDataSource;
    private UserDataSource.RemoteDataSource mRemoteDataSource;

    public UserRepository(UserDataSource.LocalDataSource localDataSource,
            UserDataSource.RemoteDataSource remoteDataSource) {
        mLocalDataSource = localDataSource;
        mRemoteDataSource = remoteDataSource;
    }

    public Observable<List<User>> searchUsers(String keyWord, int limit) {
        return mRemoteDataSource.searchUsers(keyWord, limit);
    }
}
