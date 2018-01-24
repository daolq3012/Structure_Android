package com.fstyle.structure_android.data.source.remote;

import android.content.Context;
import com.fstyle.structure_android.data.model.User;
import com.fstyle.structure_android.data.source.UserDataSource;
import com.fstyle.structure_android.data.source.remote.config.service.NameApi;
import com.fstyle.structure_android.data.source.remote.config.service.AppServiceClient;
import io.reactivex.Flowable;
import io.reactivex.Single;
import java.util.List;

/**
 * Created by daolq on 1/10/18.
 */

public class UserRemoteDataSource implements UserDataSource.RemoteDataSource {

    private static UserRemoteDataSource instance;

    private NameApi mNameApi;

    public UserRemoteDataSource(NameApi nameApi) {
        mNameApi = nameApi;
    }

    public static synchronized UserRemoteDataSource getInstance(Context context) {
        if (instance == null) {
            instance = new UserRemoteDataSource(AppServiceClient.getNameApiInstance(context));
        }
        return instance;
    }

    /**
     * Used to force {@link #getInstance(Context)} to create a new instance
     * next time it's called.
     */
    public static void destroyInstance() {
        instance = null;
    }

    @Override
    public Flowable<List<User>> getUsers() {
        // TODO hard code change value to test in here!
        return mNameApi.searchGithubUsers("abc", "120")
                .flatMap(searchUserResponse -> Single.just(searchUserResponse.getItems()))
                .toFlowable();
    }
}
