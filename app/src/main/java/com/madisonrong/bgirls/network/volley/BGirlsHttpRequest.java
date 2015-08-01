package com.madisonrong.bgirls.network.volley;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

/**
 * Created by MadisonRong on 15/7/28.
 */
public class BGirlsHttpRequest {
    private static BGirlsHttpRequest instance;
    private static Context ctx;
    private RequestQueue requestQueue;
    private ImageLoader imageLoader;

    public static BGirlsHttpRequest getInstance(Context context) {
        if (instance == null) {
            synchronized (BGirlsHttpRequest.class) {
                if (instance == null) {
                    return new BGirlsHttpRequest(context);
                }
            }
        }
        return instance;
    }

    private BGirlsHttpRequest(Context context) {
        ctx = context;
        requestQueue = getRequestQueue();
        imageLoader = new ImageLoader(requestQueue, new ImageLoader.ImageCache() {
            private LruCache<String, Bitmap> cache = new LruCache<String, Bitmap>(30);

            @Override
            public Bitmap getBitmap(String url) {
                return cache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                cache.put(url, bitmap);
            }
        });
    }

    public RequestQueue getRequestQueue() {
//        if (requestQueue == null) {
//            Cache cache = new DiskBasedCache(ctx.getCacheDir(), 1024 * 1024);
//            Network network = new BasicNetwork(new HurlStack());
//            return new RequestQueue(cache, network);
//        }
//        return requestQueue;
        if (requestQueue == null) {
            return Volley.newRequestQueue(ctx, new HurlStack(), 1024 * 1024);
        }
        return requestQueue;
    }

    public <T> void addToRequestsQueue(Request<T> request){
        getRequestQueue().add(request);
    }

    public ImageLoader getImageLoader(){
        return imageLoader;
    }
}
