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
    @GET("/category/siwa/{page}.html")
    Observable<ResponseBody> getPage(@Path("page") String page);

    @GET("/pic/{id}.html")
    Observable<ResponseBody> getGirl(@Path("id") String id);

    @GET("/api.php")
    Observable<ResponseBody> getGirlPic(@Query("op") String op,
                                        @Query("id") String id);
}
