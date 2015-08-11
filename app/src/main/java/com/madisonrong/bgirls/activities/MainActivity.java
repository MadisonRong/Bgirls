package com.madisonrong.bgirls.activities;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.madisonrong.bgirls.R;
import com.madisonrong.bgirls.fragments.HomeFragment;


public class MainActivity extends AppCompatActivity
        implements HomeFragment.OnHomeFragmentItemClickListener {

    private FrameLayout mContainer;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private NavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContainer = (FrameLayout) findViewById(R.id.frame_content);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mActionBarDrawerToggle = new ActionBarDrawerToggle(MainActivity.this, mDrawerLayout,
                toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        mActionBarDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);

        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);
        setupDrawerContent(mNavigationView);
    }

    //设置NavigationView点击事件
    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        switch (menuItem.getItemId()) {
                            case R.id.navigation_item_home:
                                fragmentManager.beginTransaction()
                                        .replace(R.id.frame_content, HomeFragment.newInstance())
                                        .addToBackStack("home")
                                        .commit();
                                break;
                            case R.id.navigation_item_web:
                                WebActivity.actionStart(MainActivity.this);
                                break;
                            case R.id.navigation_item_about:
                                Snackbar.make(MainActivity.this.findViewById(R.id.frame_content),
                                        R.string.toast_disclaimers, Snackbar.LENGTH_SHORT).show();
                                break;

                        }
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.global, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_disclaimers:
                Snackbar.make(mContainer, R.string.toast_disclaimers, Snackbar.LENGTH_SHORT).show();
                return true;
            default: return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onHomeFragmentItemClick(String url) {
        Snackbar.make(mContainer, R.string.toast_disclaimers, Snackbar.LENGTH_SHORT).show();
    }
}
