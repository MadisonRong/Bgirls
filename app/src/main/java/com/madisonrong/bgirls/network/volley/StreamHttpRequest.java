package com.madisonrong.bgirls.network.volley;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;

import java.io.InputStream;

/**
 * Created by MadisonRong on 15/7/29.
 */
public class StreamHttpRequest extends StringRequest {

    public StreamHttpRequest(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        String data;
        byte[] bytes = response.data;
        data = new String(bytes);
        return Response.success(data, HttpHeaderParser.parseCacheHeaders(response));
    }
}
