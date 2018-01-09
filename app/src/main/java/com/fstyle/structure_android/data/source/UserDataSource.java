package com.fstyle.structure_android.data.source;

import android.support.annotation.NonNull;

import com.fstyle.structure_android.data.model.User;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import java.util.List;

/**
 * Created by le.quang.dao on 10/03/2017.
 */

public interface UserDataSource {
    /**
     * LocalData For User
     */
    interface LocalDataSource {

        Completable insertUser(@NonNull User user);

        Completable updateUser(@NonNull User user);

        Completable deleteUser(@NonNull User user);

        Completable insertOrUpdateUser(@NonNull User user);

        Maybe<List<User>> getAllUser();

        Maybe<User> getUserByUserLogin(String userLogin);
    }

    /**
     * RemoteData For User
     */
    interface RemoteDataSource {
        Single<List<User>> searchUsers(String keyWord, String limit);
    }
}
