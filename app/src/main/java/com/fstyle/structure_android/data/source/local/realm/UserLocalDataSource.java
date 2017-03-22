package com.fstyle.structure_android.data.source.local.realm;

import android.support.annotation.NonNull;
import com.fstyle.structure_android.data.model.User;
import com.fstyle.structure_android.data.source.UserDataSource;
import io.realm.RealmObject;
import io.realm.RealmResults;
import java.util.List;
import javax.inject.Inject;
import rx.Observable;

/**
 * Created by le.quang.dao on 14/03/2017.
 */

public class UserLocalDataSource implements UserDataSource.LocalDataSource {

    private RealmApi mRealmApi;

    @Inject
    public UserLocalDataSource(RealmApi realmApi) {
        this.mRealmApi = realmApi;
    }

    @Override
    public void openTransaction() {
        if (mRealmApi == null) {
            mRealmApi = new RealmApi();
        }
    }

    @Override
    public void closeTransaction() {
        mRealmApi.closeRealmOnMainThread();
    }

    @Override
    public void openReadTransaction() {
        // No-op
    }

    @Override
    public Observable<Void> insertUser(@NonNull final User user) {
        return mRealmApi.realmTransactionAsync((subscriber, realm) -> {
            realm.insert(user);
            subscriber.onCompleted();
        });
    }

    @Override
    public Observable<Void> updateUser(@NonNull final User user) {
        return mRealmApi.realmTransactionAsync(((subscriber, realm) -> {
            realm.insertOrUpdate(user);
            subscriber.onCompleted();
        }));
    }

    @Override
    public Observable<Void> deleteUser(@NonNull final User user) {
        return mRealmApi.realmTransactionAsync((subscriber, realm) -> {
            RealmObject.deleteFromRealm(user);
            subscriber.onCompleted();
        });
    }

    @Override
    public Observable<Void> insertOrUpdateUser(@NonNull final User user) {
        return mRealmApi.realmTransactionAsync((subscriber, realm) -> {
            realm.insertOrUpdate(user);
            subscriber.onCompleted();
        });
    }

    @Override
    public Observable<List<User>> getAllUser() {
        return mRealmApi.realmGet((subscriber, realm) -> {
            RealmResults<User> users = realm.where(User.class).findAll();
            subscriber.onNext(users);
            subscriber.onCompleted();
        });
    }

    @Override
    public Observable<User> getUserByUserLogin(final String userLogin) {
        return mRealmApi.realmGet((subscriber, realm) -> {
            User user = realm.where(User.class).equalTo("login", userLogin).findFirst();
            subscriber.onNext(user);
            subscriber.onCompleted();
        });
    }
}
