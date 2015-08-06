package com.madisonrong.bgirls.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.madisonrong.bgirls.R;
import com.madisonrong.bgirls.managers.BGirlsNetWorkManager;
import com.madisonrong.bgirls.views.adapters.BGirlsDetailRecyclerViewAdapter;

public class DetailActivity extends ActionBarActivity {

    public static String URL = "url";
    private RecyclerView recyclerView;
    private BGirlsDetailRecyclerViewAdapter bGirlsDetailRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final ImageView imageView = (ImageView) findViewById(R.id.show_girl_container);

        recyclerView = (RecyclerView) findViewById(R.id.activity_detail_recyclerview);
        recyclerView.setLayoutManager(new GridLayoutManager(DetailActivity.this, 1, OrientationHelper.HORIZONTAL, false));
        bGirlsDetailRecyclerViewAdapter = new BGirlsDetailRecyclerViewAdapter(DetailActivity.this, imageView);
        recyclerView.setAdapter(bGirlsDetailRecyclerViewAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        Intent intent = getIntent();
        String url = intent.getStringExtra(DetailActivity.URL);
        int position = url.lastIndexOf("/");
        String id = url.substring(position);
        Log.e("bgils.id", id);

        BGirlsNetWorkManager bGirlsNetWorkManager = new BGirlsNetWorkManager(DetailActivity.this, bGirlsDetailRecyclerViewAdapter);
        bGirlsNetWorkManager.getDetail(id);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.global, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Log.e("bgirls.test", "hit the shit home button to up...");
//                Intent upIntent = NavUtils.getParentActivityIntent(this);
//                if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
//                    Log.e("bgirls.test","fucking different task");
//                    // 这个activity不是这个app任务的一部分, 所以当向上导航时创建
//                    // 用合成后退栈(synthesized back stack)创建一个新任务。
//                    TaskStackBuilder.create(this)
//                            // 添加这个activity的所有父activity到后退栈中
//                            .addNextIntentWithParentStack(upIntent)
//                                    // 向上导航到最近的一个父activity
//                            .startActivities();
//                } else {
//                    Log.e("bgirls.test","fucking same current task");
//                    // 这个activity是这个app任务的一部分, 所以
//                    // 向上导航至逻辑父activity.
//                    NavUtils.navigateUpTo(this, upIntent);
//                }
                finish();
                return true;
            case R.id.action_disclaimers:
                Toast.makeText(DetailActivity.this, R.string.toast_disclaimers, Toast.LENGTH_SHORT).show();
                break;
            default: break;
        }

        return super.onOptionsItemSelected(item);
    }

    public static void actionStart(Context context, String url){
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(DetailActivity.URL, url);
        context.startActivity(intent);
    }
}
