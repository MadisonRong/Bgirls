package com.madisonrong.bgirls2.db.migrations;

import io.realm.DynamicRealm;
import io.realm.RealmMigration;
import io.realm.RealmSchema;

/**
 * Created by MadisonRong on 7/4/16.
 */
public class BgirlRealmMigration implements RealmMigration {

    public static final int SCHEMA_VERSION = 0;

    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
        // DynamicRealm exposes an editable schema
        RealmSchema schema = realm.getSchema();
        if (oldVersion == 0) {
            schema.create("girl_albums")
                    .addField("title", String.class)
                    .addField("intro", String.class)
                    .addField("comment", String.class)
                    .addField("width", String.class)
                    .addField("height", String.class)
                    .addField("thumb_50", String.class)
                    .addField("thumb_160", String.class)
                    .addField("image_url", String .class)
                    .addField("createtime", String.class)
                    .addField("source", String.class)
                    .addField("id", Integer.class);
            schema.create("girls")
                    .addField("id", Integer.class)
                    .addField("description", String.class)
                    .addField("url", String.class)
                    .addField("image_url", String.class)
                    .addRealmListField("girlImages", schema.get("girl_albums"));
            oldVersion++;
        }
    }
}
