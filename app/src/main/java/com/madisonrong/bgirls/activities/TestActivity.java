package com.madisonrong.bgirls.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

import com.madisonrong.bgirls.R;
import com.madisonrong.bgirls.network.retrofit.BGirlsClient;
import com.madisonrong.bgirls.network.retrofit.RetrofitGenerator;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class TestActivity extends ActionBarActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        webView = (WebView) findViewById(R.id.test_webview);
        final String url = "http://sexy.faceks.com";
//        webView.loadUrl(url);
        webView.setInitialScale(25);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setJavaScriptEnabled(true);

        BGirlsClient client = RetrofitGenerator.getService(BGirlsClient.class, url);
        client.getPage(1, new Callback<String>() {
            @Override
            public void success(String string, Response response) {
                webView.loadData(string, "text/html", "utf-8");
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_test, menu);
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        webView = null;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static void actionStart(Context context){
        Intent intent = new Intent(context, TestActivity.class);
        context.startActivity(intent);
    }
}
