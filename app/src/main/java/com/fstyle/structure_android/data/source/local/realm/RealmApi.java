package com.fstyle.structure_android.data.source.local.realm;

import io.realm.Realm;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action2;

/**
 * Created by le.quang.dao on 14/03/2017.
 */

public interface RealmApi {
    <T> Observable<T> realmTransaction(Action2<Subscriber<? super T>, Realm> action);

    <T> Observable<T> realmOnNewThread(Action2<Subscriber<? super T>, Realm> action);

    void closeRealmOnMainThread();
}
