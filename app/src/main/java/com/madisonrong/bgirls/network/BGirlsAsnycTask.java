package com.madisonrong.bgirls.network;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;

import com.madisonrong.bgirls.network.retrofit.BGirlsClient;
import com.madisonrong.bgirls.network.retrofit.RetrofitGenerator;

/**
 * Created by MadisonRong on 15/7/29.
 */
public class BGirlsAsnycTask extends AsyncTask<String, Void, Void> {
    private String webPage;
    private WebView webView;

    public BGirlsAsnycTask(View view) {
        webView = (WebView) view;
    }

    @Override
    protected Void doInBackground(String... params) {
        BGirlsClient client = RetrofitGenerator.getService(BGirlsClient.class, params[0]);
//        webPage = client.getPage(params[1], null);
//        Log.e("bgirls.test", webPage);
        return null;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
        Log.e("bgirls.test.task", "running progress update.....");
        webView.loadData(webPage, "text/html", "GB2313");
    }

    @Override
    protected void onPostExecute(Void s) {
        super.onPostExecute(s);
        Log.e("bgirls.test.task", "running post excute.....");
    }
}
