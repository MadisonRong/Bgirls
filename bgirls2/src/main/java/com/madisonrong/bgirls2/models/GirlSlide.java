package com.madisonrong.bgirls2.models;

/**
 * Created by MadisonRong on 6/30/16.
 */
public class GirlSlide {

    private String title;
    private String createtime;
    private String click;
    private String like;
    private String url;

    public GirlSlide() {
    }

    public GirlSlide(String title, String createtime, String click, String like, String url) {
        this.title = title;
        this.createtime = createtime;
        this.click = click;
        this.like = like;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getClick() {
        return click;
    }

    public void setClick(String click) {
        this.click = click;
    }

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
