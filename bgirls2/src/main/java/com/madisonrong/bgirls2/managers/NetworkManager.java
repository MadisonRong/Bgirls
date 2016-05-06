package com.madisonrong.bgirls2.managers;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.madisonrong.bgirls2.models.Girl;
import com.madisonrong.bgirls2.network.BGirlsClient;
import com.madisonrong.bgirls2.network.RetrofitGenerator;
import com.madisonrong.bgirls2.views.adapters.BGirlsRecyclerViewAdapter;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by MadisonRong on 5/5/16.
 */
public class NetworkManager {

    private static final String TAG = "NetworkManager";

    private Context ctx;
    private BGirlsRecyclerViewAdapter adapter;
    public NetworkManager(Context ctx, BGirlsRecyclerViewAdapter adapter) {
        this.ctx = ctx;
        this.adapter = adapter;
    }

    public void getGirlList(int page) {
        BGirlsClient client = RetrofitGenerator.getService(BGirlsClient.class);
        client.getPage(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted() called with: " + "");
                        Toast.makeText(ctx, "getting list completed", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError() called with: " + "e = [" + e + "]");
                        Toast.makeText(ctx, "getting list occur an error: " + e, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        Log.d(TAG, "onNext() called with: " + "responseBody = [" + responseBody + "]");
                        try {
                            String responseText = responseBody.string();
                            Pattern pattern = Pattern.compile("<a class=\"img\" href=\"(.*?)\">[\\s|\\S]*?<img src=\"(.*?)\" />");
                            Matcher matcher = pattern.matcher(responseText);
                            Pattern pattern1 = Pattern.compile("<div class=\"text\">[\\s|\\S]*?<p>(.*?)<br />");
                            Matcher matcher1 = pattern1.matcher(responseText);
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
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                });
    }
}
