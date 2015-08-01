package com.madisonrong.bgirls.network.retrofit;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by MadisonRong on 15/7/29.
 */
public interface BGirlsClient {
    @GET("/")
    void getPage(@Query("page") String page, Callback<String> callback);

    @GET("/post/{id}")
    void getGirl(@Path("id") String id, Callback<String> callback);
}
