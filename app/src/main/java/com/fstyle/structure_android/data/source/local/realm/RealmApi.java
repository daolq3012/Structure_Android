package com.fstyle.structure_android.data.source.local.realm;

import io.realm.Realm;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action2;

/**
 * Created by le.quang.dao on 14/03/2017.
 */

public class RealmApi {

    private Realm mRealm;

    public RealmApi() {
        mRealm = Realm.getDefaultInstance();
    }

    /**
     * <h1>USE THIS METHOD FOR <b>INSERT/UPDATE/DELETE (On Background Thread)</b></h1><br>
     * Use this method for insert/delete action on action.
     * <br><b>BIG PROBLEM</b>: be careful with this method. action object cannot use across thread.
     * this
     * method
     * execute a async transaction so, if you let subscribers onNext with action object. Realm
     * object will become invalid and cause bug.
     */
    public <T> Observable<T> realmTransactionAsync(
            final Action2<Subscriber<? super T>, Realm> action) {
        return Observable.create(subscriber -> mRealm.executeTransactionAsync(
                realm -> action.call(subscriber, realm), subscriber::onCompleted,
                subscriber::onError));
    }

    /**
     * USE THIS METHOD FOR GET
     */
    public <T> Observable<T> realmGet(final Action2<Subscriber<? super T>, Realm> action) {
        return Observable.create(subscriber -> action.call(subscriber, mRealm));
    }

    public void closeRealmOnMainThread() {
        if (mRealm != null && !mRealm.isClosed()) {
            mRealm.close();
            mRealm = null;
        }
    }
}
