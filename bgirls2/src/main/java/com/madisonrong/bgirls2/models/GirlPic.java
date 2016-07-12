package com.madisonrong.bgirls2.models;

import android.widget.ImageView;

/**
 * Created by MadisonRong on 6/30/16.
 */
public class GirlPic {

    private ImageView pic;
    private ImageView indicator;

    public GirlPic() {
    }

    public GirlPic(ImageView pic, ImageView indicator) {
        this.pic = pic;
        this.indicator = indicator;
    }

    public ImageView getPic() {
        return pic;
    }

    public void setPic(ImageView pic) {
        this.pic = pic;
    }

    public ImageView getIndicator() {
        return indicator;
    }

    public void setIndicator(ImageView indicator) {
        this.indicator = indicator;
    }
}
