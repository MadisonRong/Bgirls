package com.madisonrong.bgirls.managers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.design.widget.TabLayout;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.madisonrong.bgirls.R;
import com.madisonrong.bgirls.activities.DetailActivity;
import com.madisonrong.bgirls.constant.BGirls;
import com.madisonrong.bgirls.models.Girl;
import com.madisonrong.bgirls.network.retrofit.BGirlsClient;
import com.madisonrong.bgirls.network.retrofit.RetrofitGenerator;
import com.madisonrong.bgirls.network.volley.BGirlsHttpRequest;
import com.madisonrong.bgirls.views.adapters.BGirlsPagerAdapter;
import com.madisonrong.bgirls.views.adapters.BGirlsRecyclerViewAdapter;
import com.madisonrong.bgirls.views.adapters.BaseRecyclerViewAdapter;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by MadisonRong on 15/8/2.
 */
public class BGirlsNetWorkManager {

    private Context ctx;
    private BaseRecyclerViewAdapter adapter;
    private BGirlsPagerAdapter bGirlsPagerAdapter;
    private TabLayout tabLayout;

    public BGirlsNetWorkManager(Context ctx, BaseRecyclerViewAdapter adapter) {
        this.adapter = adapter;
        this.ctx = ctx;
    }

    public BGirlsNetWorkManager(Context ctx, BGirlsPagerAdapter bGirlsPagerAdapter) {
        this.ctx = ctx;
        this.bGirlsPagerAdapter = bGirlsPagerAdapter;
    }

    public void setTabLayout(TabLayout tabLayout) {
        this.tabLayout = tabLayout;
    }

    /**
     * 获取专辑列表数据的网络请求部分
     * @param page 请求的页数
     */
    public synchronized void getList(int page) {
        BGirlsClient client = RetrofitGenerator.getService(BGirlsClient.class, BGirls.HOME_BASE_URL);
        client.getPage(page, new Callback<String>() {
            @Override
            public void success(String string, Response response) {
                Pattern pattern = Pattern.compile("<a class=\"img\" href=\"(.*?)\">[\\s|\\S]*?<img src=\"(.*?)\" />");
                Matcher matcher = pattern.matcher(string);
                Pattern pattern1 = Pattern.compile("<div class=\"text\">[\\s|\\S]*?<p>(.*?)<br />");
                Matcher matcher1 = pattern1.matcher(string);
                while (matcher.find() && matcher1.find()) {
                    String url = matcher.group(1);
                    String description = matcher1.group(1);
                    String img = matcher.group(2);

                    Girl girl = new Girl();
                    girl.setUrl(url);
                    girl.setDescription(description);
                    girl.setImgUrl(img);
                    adapter.add(girl);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(ctx, R.string.toast_network_error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 获取某个专辑的详细信息的网络请求部分
     * @param id 专辑id
     */
    public synchronized void getDetail(String id) {
        BGirlsClient client= RetrofitGenerator.getService(BGirlsClient.class, BGirls.HOME_BASE_URL);
        client.getGirl(id, new Callback<String>() {
            @Override
            public void success(String s, Response response) {
                Pattern pattern = Pattern.compile("<img src=\"(.*?)\"/>");
                Matcher matcher = pattern.matcher(s);
                while (matcher.find()) {
                    String imgUrl = matcher.group(1);
                    ImageView imageView = new ImageView(ctx);
                    Picasso.with(ctx)
                            .load(imgUrl)
                            .into(imageView);
                    bGirlsPagerAdapter.add(imageView);
                    if (tabLayout != null) {
                        ImageView imageView1 = new ImageView(ctx);
                        Picasso.with(ctx)
                                .load(imgUrl.replace("thumbnail=1680x0&quality=96", "thumbnail=100x0&quality=100"))
                                .into(imageView1);
                        tabLayout.addTab(tabLayout.newTab().setCustomView(imageView1));
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(ctx, R.string.toast_network_error, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
