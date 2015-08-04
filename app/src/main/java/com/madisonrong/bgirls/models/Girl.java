package com.madisonrong.bgirls.models;

import android.graphics.Bitmap;

/**
 * Created by MadisonRong on 15/7/30.
 */
public class Girl {

    private String description;
    private Bitmap picture;
    private String url;
    private String imgUrl;

    public Girl() {
    }

    public Girl(String description, Bitmap picture, String url) {
        this.description = description;
        this.picture = picture;
        this.url = url;
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
}
