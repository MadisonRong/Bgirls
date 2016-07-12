package com.madisonrong.bgirls2.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by MadisonRong on 6/30/16.
 */
public class GirlImagesRealmObject extends RealmObject {

    private String title;
    private String intro;
    private String comment;
    private String width;
    private String height;
    private String thumb_50;
    private String thumb_160;
    @PrimaryKey
    private String image_url;
    private String createtime;
    private String source;
    private int id;

    public GirlImagesRealmObject() {
    }

    public GirlImagesRealmObject(String title, String intro, String comment, String width, String height, String thumb_50, String thumb_160, String image_url, String createtime, String source, int id) {
        this.title = title;
        this.intro = intro;
        this.comment = comment;
        this.width = width;
        this.height = height;
        this.thumb_50 = thumb_50;
        this.thumb_160 = thumb_160;
        this.image_url = image_url;
        this.createtime = createtime;
        this.source = source;
        this.id = id;
    }

    public GirlImagesRealmObject(GirlImages girlImages) {
        this.title = girlImages.getTitle();
        this.intro = girlImages.getIntro();
        this.comment = girlImages.getComment();
        this.width = girlImages.getWidth();
        this.height = girlImages.getHeight();
        this.thumb_50 = girlImages.getThumb_50();
        this.thumb_160 = girlImages.getThumb_160();
        this.image_url = girlImages.getImage_url();
        this.createtime = girlImages.getCreatetime();
        this.source = girlImages.getSource();
        this.id = girlImages.getId();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getThumb_50() {
        return thumb_50;
    }

    public void setThumb_50(String thumb_50) {
        this.thumb_50 = thumb_50;
    }

    public String getThumb_160() {
        return thumb_160;
    }

    public void setThumb_160(String thumb_160) {
        this.thumb_160 = thumb_160;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "GirlImages{" +
                "title='" + title + '\'' +
                ", intro='" + intro + '\'' +
                ", comment='" + comment + '\'' +
                ", width='" + width + '\'' +
                ", height='" + height + '\'' +
                ", thumb_50='" + thumb_50 + '\'' +
                ", thumb_160='" + thumb_160 + '\'' +
                ", image_url='" + image_url + '\'' +
                ", createtime='" + createtime + '\'' +
                ", source='" + source + '\'' +
                ", id=" + id +
                '}';
    }
}
