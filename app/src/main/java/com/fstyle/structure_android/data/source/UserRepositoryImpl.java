package com.fstyle.structure_android.data.source;

import com.fstyle.structure_android.data.model.User;
import com.fstyle.structure_android.data.source.local.realm.UserLocalDataSource;
import com.fstyle.structure_android.data.source.remote.UserRemoteDataSource;
import java.util.List;
import rx.Observable;

/**
 * Created by le.quang.dao on 10/03/2017.
 */

public class UserRepositoryImpl implements UserRepository {

    private UserDataSource.LocalDataSource mLocalDataSource;
    private UserDataSource.RemoteDataSource mRemoteDataSource;

    public UserRepositoryImpl(UserLocalDataSource localDataSource,
            UserRemoteDataSource remoteDataSource) {
        mLocalDataSource = localDataSource;
        mRemoteDataSource = remoteDataSource;
    }

    public Observable<List<User>> searchUsers(int limit, String keyWord) {
        return mRemoteDataSource.searchUsers(limit, keyWord);
    }
}
