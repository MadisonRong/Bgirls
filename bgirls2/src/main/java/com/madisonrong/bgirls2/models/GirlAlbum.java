package com.madisonrong.bgirls2.models;

/**
 * Created by MadisonRong on 6/30/16.
 */
public class GirlAlbum {

//    private String interface;
    private String title;
    private String url;
    private String thumb_50;

    public GirlAlbum() {
    }

    public GirlAlbum(String title, String url, String thumb_50) {
        this.title = title;
        this.url = url;
        this.thumb_50 = thumb_50;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getThumb_50() {
        return thumb_50;
    }

    public void setThumb_50(String thumb_50) {
        this.thumb_50 = thumb_50;
    }
}
