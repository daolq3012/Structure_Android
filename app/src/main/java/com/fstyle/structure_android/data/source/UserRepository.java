package com.fstyle.structure_android.data.source;

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

    public UserDataSource.LocalDataSource getLocalDataSource() {
        return mLocalDataSource;
    }

    public UserDataSource.RemoteDataSource getRemoteDataSource() {
        return mRemoteDataSource;
    }
}
