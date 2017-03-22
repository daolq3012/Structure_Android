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
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(final Subscriber<? super T> subscriber) {
                mRealm.executeTransactionAsync(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        action.call(subscriber, realm);
                    }
                }, new Realm.Transaction.OnSuccess() {
                    @Override
                    public void onSuccess() {
                        subscriber.onCompleted();
                    }
                }, new Realm.Transaction.OnError() {
                    @Override
                    public void onError(Throwable error) {
                        subscriber.onError(error);
                    }
                });
            }
        });
    }

    /**
     * USE THIS METHOD FOR GET
     */
    public <T> Observable<T> realmGet(final Action2<Subscriber<? super T>, Realm> action) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                action.call(subscriber, mRealm);
                subscriber.onCompleted();
            }
        });
    }

    public void closeRealmOnMainThread() {
        if (mRealm != null && !mRealm.isClosed()) {
            mRealm.close();
            mRealm = null;
        }
    }
}
