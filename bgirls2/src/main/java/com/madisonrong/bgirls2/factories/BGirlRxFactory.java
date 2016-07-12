package com.madisonrong.bgirls2.factories;

import io.realm.DynamicRealm;
import io.realm.DynamicRealmObject;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.rx.RxObservableFactory;
import rx.Observable;

/**
 * Created by MadisonRong on 7/4/16.
 */
public class BGirlRxFactory implements RxObservableFactory {
    @Override
    public Observable<Realm> from(Realm realm) {
        return null;
    }

    @Override
    public Observable<DynamicRealm> from(DynamicRealm realm) {
        return null;
    }

    @Override
    public <E extends RealmModel> Observable<RealmResults<E>> from(Realm realm, RealmResults<E> results) {
        return null;
    }

    @Override
    public Observable<RealmResults<DynamicRealmObject>> from(DynamicRealm realm, RealmResults<DynamicRealmObject> results) {
        return null;
    }

    @Override
    public <E extends RealmModel> Observable<RealmList<E>> from(Realm realm, RealmList<E> list) {
        return null;
    }

    @Override
    public Observable<RealmList<DynamicRealmObject>> from(DynamicRealm realm, RealmList<DynamicRealmObject> list) {
        return null;
    }

    @Override
    public <E extends RealmModel> Observable<E> from(Realm realm, E object) {
        return null;
    }

    @Override
    public Observable<DynamicRealmObject> from(DynamicRealm realm, DynamicRealmObject object) {
        return null;
    }

    @Override
    public <E extends RealmModel> Observable<RealmQuery<E>> from(Realm realm, RealmQuery<E> query) {
        return null;
    }

    @Override
    public Observable<RealmQuery<DynamicRealmObject>> from(DynamicRealm realm, RealmQuery<DynamicRealmObject> query) {
        return null;
    }
}
