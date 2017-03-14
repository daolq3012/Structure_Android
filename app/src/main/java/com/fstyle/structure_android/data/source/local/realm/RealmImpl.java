package com.fstyle.structure_android.data.source.local.realm;

import android.os.Looper;
import io.realm.Realm;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Action2;
import rx.schedulers.Schedulers;

/**
 * Created by le.quang.dao on 14/03/2017.
 */

public class RealmImpl implements RealmApi {

    private Realm mRealm;

    public RealmImpl() {
    }

    private void action(Action1<Realm> action) {
        if (isOnMainThread()) {
            if (mRealm == null || mRealm.isClosed()) {
                mRealm = Realm.getDefaultInstance();
            }
            action.call(mRealm);
            return;
        }
        action.call(Realm.getDefaultInstance());
    }

    private boolean isOnMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
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
    @Override
    public <T> Observable<T> realmTransaction(final Action2<Subscriber<? super T>, Realm> action) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(final Subscriber<? super T> subscriber) {
                action(new Action1<Realm>() {
                    @Override
                    public void call(final Realm realm) {
                        realm.executeTransactionAsync(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                try {
                                    action.call(subscriber, realm);
                                    subscriber.onCompleted();
                                } catch (RuntimeException e) {
                                    subscriber.onError(e);
                                }
                            }
                        });
                    }
                });
            }
        }).subscribeOn(AndroidSchedulers.mainThread());
    }

    /**
     * <h1>USE THIS METHOD ONLY FOR <b>QUERY (without any change to database)</b></h1><br>
     * Execute an realm action on new thread.
     * Auto close action instance when finish transaction.
     */
    @Override
    public <T> Observable<T> realmOnNewThread(final Action2<Subscriber<? super T>, Realm> action) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(final Subscriber<? super T> subscriber) {
                action(new Action1<Realm>() {
                    @Override
                    public void call(Realm realm) {
                        try {
                            action.call(subscriber, realm);
                            subscriber.onCompleted();
                        } catch (RuntimeException e) {
                            subscriber.onError(e);
                        }
                        realm.close();
                    }
                });
            }
        }).subscribeOn(Schedulers.newThread());
    }

    @Override
    public void closeRealmOnMainThread() {
        if (isOnMainThread() && mRealm != null && !mRealm.isClosed()) {
            mRealm.close();
            mRealm = null;
        }
    }
}
