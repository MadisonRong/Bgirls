package com.madisonrong.bgirls2.models;

import java.util.List;

/**
 * Created by MadisonRong on 6/30/16.
 */
public class GirlInfo {

    private GirlSlide slide;
    private GirlAlbum next_album;
    private GirlAlbum prev_album;
    private List<GirlImages> images;

    public GirlInfo() {
    }

    public GirlInfo(GirlSlide slide, GirlAlbum next_album, GirlAlbum prev_album, List<GirlImages> images) {
        this.slide = slide;
        this.next_album = next_album;
        this.prev_album = prev_album;
        this.images = images;
    }

    public GirlSlide getSlide() {
        return slide;
    }

    public void setSlide(GirlSlide slide) {
        this.slide = slide;
    }

    public GirlAlbum getNext_album() {
        return next_album;
    }

    public void setNext_album(GirlAlbum next_album) {
        this.next_album = next_album;
    }

    public GirlAlbum getPrev_album() {
        return prev_album;
    }

    public void setPrev_album(GirlAlbum prev_album) {
        this.prev_album = prev_album;
    }

    public List<GirlImages> getImages() {
        return images;
    }

    public void setImages(List<GirlImages> images) {
        this.images = images;
    }
}
