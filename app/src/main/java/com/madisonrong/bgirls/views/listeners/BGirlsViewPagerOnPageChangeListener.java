package com.madisonrong.bgirls.views.listeners;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

/**
 * Created by MadisonRong on 15/8/9.
 */
public class BGirlsViewPagerOnPageChangeListener implements ViewPager.OnPageChangeListener {

    private TabLayout tabLayout;

    public BGirlsViewPagerOnPageChangeListener(TabLayout tabLayout) {
        this.tabLayout = tabLayout;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (tabLayout != null) {
            tabLayout.getTabAt(position).select();
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
