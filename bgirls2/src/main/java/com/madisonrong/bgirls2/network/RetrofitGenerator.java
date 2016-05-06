package com.madisonrong.bgirls2.network;

import com.madisonrong.bgirls2.config.BgirlConfig;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by MadisonRong on 15/7/29.
 */
public class RetrofitGenerator {

    private RetrofitGenerator() {
    }

    public static <T> T getService(Class<T> clazz) {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BgirlConfig.BASE_URL)
                .client(new OkHttpClient())
                .build();
        return retrofit.create(clazz);
    }
}
