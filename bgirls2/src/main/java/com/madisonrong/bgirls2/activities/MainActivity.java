package com.madisonrong.bgirls2.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;

import com.fivehundredpx.greedolayout.GreedoLayoutManager;
import com.fivehundredpx.greedolayout.GreedoSpacingItemDecoration;
import com.madisonrong.bgirls2.R;
import com.madisonrong.bgirls2.managers.NetworkManager;
import com.madisonrong.bgirls2.views.adapters.BGirlsRecyclerViewAdapter;
import com.madisonrong.bgirls2.views.adapters.MeasUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    @Bind(R.id.toolbar)
    public Toolbar toolbar;
    @Bind(R.id.bgirl_recycler_view)
    public RecyclerView recyclerView;
    private BGirlsRecyclerViewAdapter adapter;
    private RecyclerView.OnScrollListener scrollListener;
    private NetworkManager manager;
    private int page = 1;
    private boolean canLoad = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        adapter = new BGirlsRecyclerViewAdapter(this);
        manager = new NetworkManager(MainActivity.this, adapter);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL));
        GreedoLayoutManager layoutManager = new GreedoLayoutManager(adapter);
        recyclerView.setLayoutManager(layoutManager);
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int maxRowHeight = metrics.heightPixels / 3;
        layoutManager.setMaxRowHeight(maxRowHeight);
        int spacing = MeasUtils.dpToPx(4, this);
        recyclerView.addItemDecoration(new GreedoSpacingItemDecoration(spacing));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        scrollListener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) recyclerView.getLayoutManager();
                int[] lastVisibleItems = staggeredGridLayoutManager.findLastVisibleItemPositions(null);
                int lastItem = Math.max(lastVisibleItems[0], lastVisibleItems[1]);

                //先判断是否已经到底了
                if (dy > 0 && lastItem > adapter.getItemCount() - 5) {
                    //然后再来判断是否正在加载
                    if (canLoad) {
                        canLoad = false;
                        page += 1;
                        Log.i(TAG, "onScrolled: load page: " + page);
                        manager.getGirlList(page);
                    }
                } else {
                    //加载完成后，上面的条件会判断失败，此时就可以修改canLoad条件
                    canLoad = true;
                }

            }
        };
        recyclerView.addOnScrollListener(scrollListener);
//        recyclerView.setNestedScrollingEnabled(false);
//        recyclerView.setHasFixedSize(false);
        manager.getGirlList(page);
    }

    @Override
    protected void onPause() {
        super.onPause();
        recyclerView.removeOnScrollListener(scrollListener);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
}
