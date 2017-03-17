package com.fstyle.structure_android.data.source;

import android.support.annotation.NonNull;
import com.fstyle.structure_android.data.model.User;
import com.fstyle.structure_android.data.model.UsersList;
import java.util.List;
import rx.Observable;

/**
 * Created by le.quang.dao on 10/03/2017.
 */

public interface UserDataSource {
    /**
     * LocalData For User
     */
    interface LocalDataSource extends BaseLocalDataSource {

        Observable<Void> insertUser(@NonNull User user);

        Observable<Void> updateUser(@NonNull User user);

        Observable<Void> deleteUser(@NonNull User user);

        Observable<Void> insertOrUpdateUser(@NonNull User user);

        Observable<List<User>> getAllUser();

        Observable<User> getUserByUserLogin(String userLogin);
    }

    /**
     * RemoteData For User
     */
    interface RemoteDataSource {
        Observable<UsersList> searchUsers(int limit, String keyWord);
    }
}
