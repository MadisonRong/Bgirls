package com.madisonrong.bgirls2.db;

import android.content.Context;

import com.madisonrong.bgirls2.db.migrations.BgirlRealmMigration;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * create singleton realm
 * Created by MadisonRong on 7/4/16.
 */
public class BgirlRealm {

    private RealmConfiguration configuration;

    private BgirlRealm() {}

    public static void init(Context context) {
        BgirlRealm bgirlRealm = BgirlRealmHolder.getInstance();
        bgirlRealm.initConfig(context);
    }

    private void initConfig(Context context) {
        configuration = new RealmConfiguration.Builder(context)
                .name("bgirls.realm")
                .deleteRealmIfMigrationNeeded()
                .schemaVersion(BgirlRealmMigration.SCHEMA_VERSION)
                .migration(new BgirlRealmMigration())
                .build();
    }

    public static Realm getInstance() {
        return Realm.getInstance(BgirlRealmHolder.getInstance().configuration);
    }

    public static class BgirlRealmHolder {
        private static BgirlRealm instance = new BgirlRealm();

        public static BgirlRealm getInstance() {
            return instance;
        }
    }
}
