package com.madisonrong.bgirls2.activities;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.madisonrong.bgirls2.R;
import com.madisonrong.bgirls2.managers.CacheManager;
import com.madisonrong.bgirls2.managers.NetworkManager;
import com.madisonrong.bgirls2.views.adapters.BGirlsPagerAdapter;
import com.madisonrong.bgirls2.views.listeners.BGirlsDataListener;
import com.viewpagerindicator.CirclePageIndicator;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscription;

/**
 * Created by MadisonRong on 6/28/16.
 */
public class DetailActivity extends AppCompatActivity {
    private static final String TAG = "DetailActivity";
    public static final String URL = "url";
    public static final String DESC = "description";

    @Bind(R.id.toolbar)
    public Toolbar toolbar;
    @Bind(R.id.container)
    public RelativeLayout container;
    @Bind(R.id.activity_detail_viewpager)
    public ViewPager viewPager;
    @Bind(R.id.indicator)
    public CirclePageIndicator indicator;
    @Bind(R.id.progress_bar)
    public ProgressBar progressBar;
    private BGirlsDataListener dataListener = new BGirlsDataListener() {
        @Override
        public void onCompleted(int page) {
            progressBar.setVisibility(View.GONE);
        }

        @Override
        public void onError(Throwable e) {
            Snackbar.make(container, e.getMessage(), Snackbar.LENGTH_LONG).show();
        }
    };
    private Subscription subscription;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();
        String url = intent.getStringExtra(DetailActivity.URL);

        setTitle(intent.getStringExtra(DetailActivity.DESC));

        BGirlsPagerAdapter bGirlsPagerAdapter = new BGirlsPagerAdapter();
        viewPager.setAdapter(bGirlsPagerAdapter);

        NetworkManager netWorkManager = new NetworkManager(DetailActivity.this, bGirlsPagerAdapter);
        CacheManager cacheManager = new CacheManager(DetailActivity.this, bGirlsPagerAdapter);
        netWorkManager.setDataListener(dataListener);
        cacheManager.setDataListener(dataListener);
        if (cacheManager.hasDetailCached(url)) {
            cacheManager.getCacheDetail(url);
        } else {
            netWorkManager.getDetail(url);
        }

        indicator.setViewPager(viewPager);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if (subscription != null && subscription.isUnsubscribed()) {
//            subscription.unsubscribe();
//        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // back
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static void actionStart(Activity activity, String url, String description) {
        Intent intent = new Intent(activity, DetailActivity.class);
        intent.putExtra(DetailActivity.URL, url);
        intent.putExtra(DetailActivity.DESC, description);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(activity).toBundle());
        } else {
            activity.startActivity(intent);
        }
    }
}
