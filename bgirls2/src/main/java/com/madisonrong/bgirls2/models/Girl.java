package com.madisonrong.bgirls2.models;

import android.graphics.Bitmap;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

/**
 * Created by MadisonRong on 15/7/30.
 */
public class Girl extends RealmObject {

    @PrimaryKey
    private long id;
    private String description;
    @Ignore
    private Bitmap picture;
    private String url;
    private String imgUrl;
    private RealmList<GirlImagesRealmObject> girlImages;

    public Girl() {
    }

    public Girl(long id, String description, Bitmap picture, String url, String imgUrl) {
        this.id = id;
        this.description = description;
        this.picture = picture;
        this.url = url;
        this.imgUrl = imgUrl;
    }

    public Girl(String description, Bitmap picture, String url) {
        this.description = description;
        this.picture = picture;
        this.url = url;
    }

    public Girl(long id, String description, Bitmap picture, String url, String imgUrl, RealmList<GirlImagesRealmObject> girlImages) {
        this.id = id;
        this.description = description;
        this.picture = picture;
        this.url = url;
        this.imgUrl = imgUrl;
        this.girlImages = girlImages;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Bitmap getPicture() {
        return picture;
    }

    public void setPicture(Bitmap picture) {
        this.picture = picture;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public RealmList<GirlImagesRealmObject> getGirlImages() {
        return girlImages;
    }

    public void setGirlImages(RealmList<GirlImagesRealmObject> girlImages) {
        this.girlImages = girlImages;
    }

    @Override
    public String toString() {
        return "Girl{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", picture=" + picture +
                ", url='" + url + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", girlImages=" + girlImages +
                '}';
    }
}
