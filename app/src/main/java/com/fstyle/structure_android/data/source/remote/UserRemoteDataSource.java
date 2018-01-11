package com.fstyle.structure_android.data.source.remote;

import com.fstyle.structure_android.data.model.User;
import com.fstyle.structure_android.data.source.UserDataSource;
import com.fstyle.structure_android.data.source.remote.config.response.SearchUserResponse;
import com.fstyle.structure_android.data.source.remote.config.service.NameApi;
import com.fstyle.structure_android.data.source.remote.config.service.NameServiceClient;
import io.reactivex.Maybe;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
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

    public static synchronized UserRemoteDataSource getInstance() {
        if (instance == null) {
            instance = new UserRemoteDataSource(NameServiceClient.getInstance());
        }
        return instance;
    }

    /**
     * Used to force {@link #getInstance()} to create a new instance
     * next time it's called.
     */
    public static void destroyInstance() {
        instance = null;
    }

    @Override
    public Maybe<List<User>> getAllUser() {
        // TODO hard code change value to test in here!
        return mNameApi.searchGithubUsers("abc", "12")
                .flatMap(new Function<SearchUserResponse, SingleSource<? extends List<User>>>() {
                    @Override
                    public SingleSource<? extends List<User>> apply(
                            SearchUserResponse searchUserResponse) throws Exception {
                        return Single.just(searchUserResponse.getItems());
                    }
                }).toMaybe();
    }
}
