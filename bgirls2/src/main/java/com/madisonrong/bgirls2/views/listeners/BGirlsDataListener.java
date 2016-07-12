package com.madisonrong.bgirls2.views.listeners;

/**
 * Created by MadisonRong on 7/11/16.
 */
public interface BGirlsDataListener {

    /**
     * callback for completed
     * @param page loading page
     */
    void onCompleted(int page);

    /**
     * callback for when something's wrong
     * @param e exception
     */
    void onError(Throwable e);
}
