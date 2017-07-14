package com.fstyle.structure_android.data.source;

import android.support.annotation.NonNull;
import com.fstyle.structure_android.data.model.User;
import io.reactivex.Observable;
import java.util.List;

/**
 * Created by le.quang.dao on 10/03/2017.
 */

public interface UserDataSource {
    /**
     * LocalData For User
     */
    interface LocalDataSource extends BaseLocalDataSource {

        Observable<Object> insertUser(@NonNull User user);

        Observable<Object> updateUser(@NonNull User user);

        Observable<Object> deleteUser(@NonNull User user);

        Observable<Object> insertOrUpdateUser(@NonNull User user);

        Observable<List<User>> getAllUser();

        Observable<User> getUserByUserLogin(String userLogin);
    }

    /**
     * RemoteData For User
     */
    interface RemoteDataSource {
        Observable<List<User>> searchUsers(String keyWord, int limit);
    }
}
