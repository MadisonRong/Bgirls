package com.madisonrong.bgirls.network.retrofit;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by MadisonRong on 15/7/29.
 */
public interface BGirlsClient {
//    @GET("/")
//    void getPage(@Query("page") int page, Callback<String> callback);
//
//    @GET("/post/{id}")
//    void getGirl(@Path("id") String id, Callback<String> callback);


    @GET("/category/siwa/{page}.html")
    void getPage(@Path("page") String page, Callback<String> callback);

    @GET("/pic/{id}.html")
    void getGirl(@Path("id") String id, Callback<String> callback);
}
