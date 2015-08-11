package com.madisonrong.bgirls.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.madisonrong.bgirls.R;
import com.madisonrong.bgirls.fragments.DetailFragment;
import com.madisonrong.bgirls.managers.BGirlsNetWorkManager;
import com.madisonrong.bgirls.views.adapters.BGirlsPagerAdapter;
import com.madisonrong.bgirls.views.listeners.BGirlsTabLayoutOnTabSelectedListener;
import com.madisonrong.bgirls.views.listeners.BGirlsViewPagerOnPageChangeListener;

public class DetailActivity extends AppCompatActivity {

    public static final String URL = "url";
    public static final String DESC = "description";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        String url = intent.getStringExtra(DetailActivity.URL);
        int position = url.lastIndexOf("/");
        String id = url.substring(position);
        Log.e("bgils.id", id);

        setTitle(intent.getStringExtra(DetailActivity.DESC));

        final TabLayout tabLayout = (TabLayout) findViewById(R.id.activity_detail_sliding_tabs);
        final ViewPager viewPager = (ViewPager) findViewById(R.id.activity_detail_viewpager);
        final BGirlsPagerAdapter bGirlsPagerAdapter = new BGirlsPagerAdapter();
        viewPager.setAdapter(bGirlsPagerAdapter);

        BGirlsNetWorkManager bGirlsNetWorkManager = new BGirlsNetWorkManager(DetailActivity.this, bGirlsPagerAdapter);
        bGirlsNetWorkManager.setTabLayout(tabLayout);
        bGirlsNetWorkManager.getDetail(id);

        tabLayout.setOnTabSelectedListener(new BGirlsTabLayoutOnTabSelectedListener(viewPager));

        viewPager.addOnPageChangeListener(new BGirlsViewPagerOnPageChangeListener(tabLayout));
        
//        viewPager.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FragmentManager fragmentManager = getSupportFragmentManager();
//                DetailFragment fragment = DetailFragment.newInstance(viewPager.getCurrentItem());
//                fragment.setupAdapter(bGirlsPagerAdapter);
//                fragmentManager.beginTransaction()
//                        .replace(R.id.activity_detail_layout, fragment)
////                        .addToBackStack("show")
//                        .commit();
//            }
//        });
//        viewPager.performClick();

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

    public static void actionStart(Context context, String url, String description){
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(DetailActivity.URL, url);
        intent.putExtra(DetailActivity.DESC, description);
        context.startActivity(intent);
    }
}
