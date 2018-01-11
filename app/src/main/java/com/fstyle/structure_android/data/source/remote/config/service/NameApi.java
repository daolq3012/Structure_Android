package com.fstyle.structure_android.data.source.remote.config.service;

import com.fstyle.structure_android.data.model.User;
import com.fstyle.structure_android.data.source.remote.config.response.SearchUserResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by le.quang.dao on 10/03/2017.
 */

public interface NameApi {
    @GET("/search/users")
    Single<SearchUserResponse> searchGithubUsers(@Query("q") String searchTerm,
            @Query("per_page") String limit);

    @GET("/users/{username}")
    Single<User> getUser(@Path("username") String username);
}
