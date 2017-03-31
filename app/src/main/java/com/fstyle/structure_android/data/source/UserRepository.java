package com.fstyle.structure_android.data.source;

import com.fstyle.structure_android.data.model.User;
import java.util.List;
import rx.Observable;

/**
 * Created by le.quang.dao on 31/03/2017.
 */

public interface UserRepository {
    Observable<List<User>> searchUsers(int limit, String keyWord);
}
