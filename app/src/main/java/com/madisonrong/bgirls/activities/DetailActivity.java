package com.madisonrong.bgirls.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.madisonrong.bgirls.R;
import com.madisonrong.bgirls.constant.BGirls;
import com.madisonrong.bgirls.models.Girl;
import com.madisonrong.bgirls.network.retrofit.BGirlsClient;
import com.madisonrong.bgirls.network.retrofit.RetrofitGenerator;
import com.madisonrong.bgirls.network.volley.BGirlsHttpRequest;
import com.madisonrong.bgirls.views.adapters.BGirlsDetailRecyclerViewAdapter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class DetailActivity extends ActionBarActivity {

    public static String URL = "url";
    private RecyclerView recyclerView;
    private BGirlsDetailRecyclerViewAdapter bGirlsDetailRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        recyclerView = (RecyclerView) findViewById(R.id.activity_detail_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(DetailActivity.this, OrientationHelper.VERTICAL, false));
        bGirlsDetailRecyclerViewAdapter = new BGirlsDetailRecyclerViewAdapter(DetailActivity.this);
        recyclerView.setAdapter(bGirlsDetailRecyclerViewAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        Intent intent = getIntent();
        String url = intent.getStringExtra(DetailActivity.URL);
        int position = url.lastIndexOf("/");
        String id = url.substring(position);
        Log.e("bgils.id", id);

        BGirlsClient client= RetrofitGenerator.getService(BGirlsClient.class, BGirls.HOME_BASE_URL);
        client.getGirl(id, new Callback<String>() {
            @Override
            public void success(String s, Response response) {
                Pattern pattern = Pattern.compile("<img src=\"(.*?)\"/>");
                Matcher matcher = pattern.matcher(s);
                while (matcher.find()) {
                    String url = matcher.group(1);
                    ImageRequest imageRequest = new ImageRequest(url,
                            new com.android.volley.Response.Listener<Bitmap>() {
                                @Override
                                public void onResponse(Bitmap response) {
                                    Girl girl = new Girl();
                                    girl.setPicture(response);
                                    bGirlsDetailRecyclerViewAdapter.add(0, girl);
                                }
                            }, 0, 0, ImageView.ScaleType.MATRIX, Bitmap.Config.RGB_565,
                            new com.android.volley.Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.e("bgirls.network.error", error.getMessage());
                                    Toast.makeText(DetailActivity.this, R.string.toast_network_error, Toast.LENGTH_SHORT).show();
                                }
                            });
                    BGirlsHttpRequest.getInstance(DetailActivity.this).addToRequestsQueue(imageRequest);
                }
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
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
