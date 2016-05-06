package com.madisonrong.bgirls2.network;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by MadisonRong on 15/7/29.
 */
public interface BGirlsClient {
    @GET("/")
    Observable<ResponseBody> getPage(@Query("page") int page);

    @GET("/post/{id}")
    Observable<ResponseBody> getGirl(@Path("id") String id);
}
