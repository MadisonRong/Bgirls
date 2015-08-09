package com.madisonrong.bgirls.views.listeners;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

/**
 * Created by MadisonRong on 15/8/9.
 */
public class BGirlsTabLayoutOnTabSelectedListener implements TabLayout.OnTabSelectedListener {

    private ViewPager viewPager;

    public BGirlsTabLayoutOnTabSelectedListener(ViewPager viewPager) {
        this.viewPager = viewPager;
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        int position = tab.getPosition();
        viewPager.setCurrentItem(position, true);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
