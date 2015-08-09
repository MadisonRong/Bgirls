package com.madisonrong.bgirls.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.madisonrong.bgirls.R;
import com.madisonrong.bgirls.managers.BGirlsNetWorkManager;
import com.madisonrong.bgirls.views.adapters.BGirlsPagerAdapter;
import com.madisonrong.bgirls.views.listeners.BGirlsTabLayoutOnTabSelectedListener;
import com.madisonrong.bgirls.views.listeners.BGirlsViewPagerOnPageChangeListener;

public class DetailActivity extends ActionBarActivity {

    public static String URL = "url";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        String url = intent.getStringExtra(DetailActivity.URL);
        int position = url.lastIndexOf("/");
        String id = url.substring(position);
        Log.e("bgils.id", id);

        final TabLayout tabLayout = (TabLayout) findViewById(R.id.activity_detail_sliding_tabs);
        final ViewPager viewPager = (ViewPager) findViewById(R.id.activity_detail_viewpager);
        BGirlsPagerAdapter bGirlsPagerAdapter = new BGirlsPagerAdapter();
        viewPager.setAdapter(bGirlsPagerAdapter);

        BGirlsNetWorkManager bGirlsNetWorkManager = new BGirlsNetWorkManager(DetailActivity.this, bGirlsPagerAdapter);
        bGirlsNetWorkManager.setTabLayout(tabLayout);
        bGirlsNetWorkManager.getDetail(id);

        tabLayout.setOnTabSelectedListener(new BGirlsTabLayoutOnTabSelectedListener(viewPager));

        viewPager.addOnPageChangeListener(new BGirlsViewPagerOnPageChangeListener(tabLayout));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // detail页面不需要menu
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //返回
                finish();
                return true;
            default: return super.onOptionsItemSelected(item);
        }
    }

    public static void actionStart(Context context, String url){
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(DetailActivity.URL, url);
        context.startActivity(intent);
    }
}
