package com.madisonrong.bgirls2;

import com.madisonrong.bgirls2.db.BgirlRealm;
import com.madisonrong.bgirls2.helpers.SharedPreferencesHelper;
import com.squareup.picasso.LruCache;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

/**
 * Created by MadisonRong on 7/4/16.
 */
public class Application extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // init configuration of Realm
//        RealmConfiguration configuration = new RealmConfiguration.Builder(getApplicationContext())
//                .name("bgirls.realm")
//                .deleteRealmIfMigrationNeeded()
//                .schemaVersion(BgirlRealmMigration.SCHEMA_VERSION)
//                .migration(new BgirlRealmMigration())
//                .build();
//        Realm.setDefaultConfiguration(configuration);
        BgirlRealm.init(getApplicationContext());

        // init Picasso
        Picasso picasso = new Picasso.Builder(getApplicationContext())
                .memoryCache(new LruCache(1024 * 1024 * 30))
                .downloader(new OkHttp3Downloader(this, 1024 * 1024 * 50))
                .indicatorsEnabled(true)
                .loggingEnabled(true)
                .build();
        Picasso.setSingletonInstance(picasso);

        // init sharedPreferences
        SharedPreferencesHelper.init(getApplicationContext());

//        LeakCanary.install(this);
    }
}
