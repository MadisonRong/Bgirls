package com.madisonrong.bgirls2.activities;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.madisonrong.bgirls2.R;
import com.madisonrong.bgirls2.helpers.SharedPreferencesHelper;
import com.madisonrong.bgirls2.managers.CacheManager;
import com.madisonrong.bgirls2.managers.NetworkManager;
import com.madisonrong.bgirls2.views.adapters.BGirlsRecyclerViewAdapter;
import com.madisonrong.bgirls2.views.listeners.BGirlsDataListener;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    @Bind(R.id.toolbar)
    public Toolbar toolbar;
    @Bind(R.id.container)
    public RelativeLayout container;
    @Bind(R.id.bgirl_recycler_view)
    public RecyclerView recyclerView;
    @Bind(R.id.bgirl_swipe_fresh_layout)
    public SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.progress_bar)
    public ProgressBar progressBar;
    private BGirlsRecyclerViewAdapter adapter;
    private RecyclerView.OnScrollListener scrollListener;
    private NetworkManager networkManager;
    private CacheManager cacheManager;
    private long lastClickTime = System.currentTimeMillis();
    private int cachedPage = 1;
    private boolean canLoad = true;
    private long totalCachedPage;
    private int currentPage = Integer.parseInt(SharedPreferencesHelper.getString("current_page", "1"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
            Transition explode = TransitionInflater.from(this).inflateTransition(R.transition.slide_explode);
            getWindow().setExitTransition(explode);
            getWindow().setReenterTransition(explode);
        }

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (System.currentTimeMillis() - lastClickTime < 300) {
                    recyclerView.smoothScrollToPosition(0);
                }
                lastClickTime = System.currentTimeMillis();
            }
        });

        adapter = new BGirlsRecyclerViewAdapter(this);
        networkManager = new NetworkManager(MainActivity.this, adapter);
        cacheManager = new CacheManager(MainActivity.this, adapter);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        totalCachedPage = cacheManager.getTotalPage();
        networkManager.setDataListener(new BGirlsDataListener() {
            @Override
            public void onCompleted(int page) {
                SharedPreferencesHelper.putString("current_page", String.valueOf(page));
                swipeRefreshLayout.setRefreshing(false);
                progressBar.setVisibility(View.GONE);
                recyclerView.scrollToPosition(0);
            }

            @Override
            public void onError(Throwable e) {
                swipeRefreshLayout.setRefreshing(false);
                Snackbar.make(container, e.getMessage(), Snackbar.LENGTH_LONG).show();
            }
        });
        cacheManager.setDataListener(new BGirlsDataListener() {
            @Override
            public void onCompleted(int page) {
                swipeRefreshLayout.setRefreshing(false);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError(Throwable e) {
                swipeRefreshLayout.setRefreshing(false);
                Snackbar.make(container, e.getMessage(), Snackbar.LENGTH_LONG).show();
            }
        });
        final int[] lastItem = new int[1];
        scrollListener = new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //先判断是否已经到底了
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastItem[0] > adapter.getItemCount() - 5 && cachedPage < totalCachedPage) {
                    //然后再来判断是否正在加载
                    if (canLoad) {
                        canLoad = false;
                        cachedPage += 1;
                        Log.i(TAG, "load cached page: " + cachedPage);
                        cacheManager.getCacheList(cachedPage);
                    }
                } else {
                    //加载完成后，上面的条件会判断失败，此时就可以修改canLoad条件
                    canLoad = true;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                StaggeredGridLayoutManager layoutManager = (StaggeredGridLayoutManager) recyclerView.getLayoutManager();
                int[] lastVisibleItems = layoutManager.findLastCompletelyVisibleItemPositions(null);
                lastItem[0] = Math.max(lastVisibleItems[0], lastVisibleItems[1]);
            }
        };
        recyclerView.addOnScrollListener(scrollListener);
        swipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_light,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light
                );
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                currentPage += 1;
                Log.i(TAG, "load online page: " + currentPage);
                networkManager.getGirlList(currentPage);
            }
        });
        Log.i(TAG, "Main activity total cached page: " + totalCachedPage);
        if (totalCachedPage == 0) {
            swipeRefreshLayout.setRefreshing(true);
            networkManager.getGirlList(currentPage);
        } else {
            cacheManager.getCacheList(cachedPage);
        }
        adapter.setOnItemClickListener(new BGirlsRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                DetailActivity.actionStart(MainActivity.this, adapter.get(position).getUrl(),
                        adapter.get(position).getDescription());
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        recyclerView.removeOnScrollListener(scrollListener);
    }

}
