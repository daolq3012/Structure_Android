package com.fstyle.structure_android.data.source.local.realm;

import android.support.annotation.NonNull;
import com.fstyle.structure_android.data.model.User;
import com.fstyle.structure_android.data.source.UserDataSource;
import io.reactivex.Observable;
import io.realm.RealmObject;
import io.realm.RealmResults;
import java.util.List;
import javax.inject.Inject;

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
    public Observable<Object> insertUser(@NonNull final User user) {
        return mRealmApi.realmTransactionAsync((observableEmitter, realm) -> {
            realm.insert(user);
            observableEmitter.onComplete();
        });
    }

    @Override
    public Observable<Object> updateUser(@NonNull final User user) {
        return mRealmApi.realmTransactionAsync(((observableEmitter, realm) -> {
            realm.insertOrUpdate(user);
            observableEmitter.onComplete();
        }));
    }

    @Override
    public Observable<Object> deleteUser(@NonNull final User user) {
        return mRealmApi.realmTransactionAsync((observableEmitter, realm) -> {
            RealmObject.deleteFromRealm(user);
            observableEmitter.onComplete();
        });
    }

    @Override
    public Observable<Object> insertOrUpdateUser(@NonNull final User user) {
        return mRealmApi.realmTransactionAsync((observableEmitter, realm) -> {
            realm.insertOrUpdate(user);
            observableEmitter.onComplete();
        });
    }

    @Override
    public Observable<List<User>> getAllUser() {
        return mRealmApi.realmGet((observableEmitter, realm) -> {
            RealmResults<User> users = realm.where(User.class).findAll();
            observableEmitter.onNext(users);
            observableEmitter.onComplete();
        });
    }

    @Override
    public Observable<User> getUserByUserLogin(final String userLogin) {
        return mRealmApi.realmGet((observableEmitter, realm) -> {
            User user = realm.where(User.class).equalTo("login", userLogin).findFirst();
            observableEmitter.onNext(user);
            observableEmitter.onComplete();
        });
    }
}
