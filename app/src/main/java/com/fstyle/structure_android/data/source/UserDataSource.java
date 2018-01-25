package com.fstyle.structure_android.data.source;

import android.support.annotation.NonNull;
import com.fstyle.structure_android.data.model.User;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import java.util.List;

/**
 * Created by le.quang.dao on 10/03/2017.
 */

public interface UserDataSource {

    Flowable<List<User>> getUsers();

    /**
     * LocalData For User
     */
    interface LocalDataSource extends UserDataSource {

        Completable insertUser(@NonNull User user);

        Completable updateUser(@NonNull User user);

        Completable deleteUser(@NonNull User user);

        Completable insertOrUpdateUser(@NonNull User user);

        Flowable<User> getUser(String userLogin);

        Flowable<List<User>> searchUsers(String userName);

        Completable deleteAllUsers();
    }

    /**
     * RemoteData For User
     */
    interface RemoteDataSource extends UserDataSource {
    }
}
