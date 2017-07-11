package com.fstyle.structure_android.data.source.local.realm;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.functions.BiConsumer;
import io.realm.Realm;

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
            final BiConsumer<ObservableEmitter<? super T>, Realm> consumer) {
        return Observable.create(emitter -> mRealm.executeTransactionAsync(realm -> {
            try {
                consumer.accept(emitter, realm);
            } catch (Exception e) {
                emitter.onError(e);
            }
        }, emitter::onComplete, emitter::onError));
    }

    /**
     * USE THIS METHOD FOR GET
     */
    public <T> Observable<T> realmGet(
            final BiConsumer<ObservableEmitter<? super T>, Realm> consumer) {
        return Observable.create(subscriber -> consumer.accept(subscriber, mRealm));
    }

    public void closeRealmOnMainThread() {
        if (mRealm != null && !mRealm.isClosed()) {
            mRealm.close();
            mRealm = null;
        }
    }
}
