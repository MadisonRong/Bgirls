package com.madisonrong.bgirls.managers;

import android.support.v7.widget.StaggeredGridLayoutManager;

/**
 * Created by MadisonRong on 15/7/31.
 */
public class BGirlStaggeredGridLayoutManager extends StaggeredGridLayoutManager {
    /**
     * Creates a StaggeredGridLayoutManager with given parameters.
     *
     * @param spanCount   If orientation is vertical, spanCount is number of columns. If
     *                    orientation is horizontal, spanCount is number of rows.
     * @param orientation {@link #VERTICAL} or {@link #HORIZONTAL}
     */
    public BGirlStaggeredGridLayoutManager(int spanCount, int orientation) {
        super(spanCount, orientation);
    }
}
