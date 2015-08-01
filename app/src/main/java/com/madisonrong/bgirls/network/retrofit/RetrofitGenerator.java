package com.madisonrong.bgirls.network.retrofit;

import android.util.Log;

import com.squareup.okhttp.OkHttpClient;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;

import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.ConversionException;
import retrofit.converter.Converter;
import retrofit.mime.TypedInput;
import retrofit.mime.TypedOutput;

/**
 * Created by MadisonRong on 15/7/29.
 */
public class RetrofitGenerator {

    private RetrofitGenerator() {
    }

    public static <T> T getService(Class<T> clazz, String baseUrl) {
        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setConverter(new Converter() {
                    @Override
                    public Object fromBody(TypedInput body, Type type) throws ConversionException {
                        try {
                            //将响应内容以读取流的方式转换为字符串并返回
                            Reader reader = new InputStreamReader(body.in());
                            StringBuilder stringBuilder = new StringBuilder();
                            char[] bytes = new char[1024];
                            while (reader.read(bytes) != -1) {
                                stringBuilder.append(bytes);
                            }
                            String text = stringBuilder.toString();
                            return text;
                        } catch (IOException e) {
                            e.printStackTrace();
                            return null;
                        }
                    }

                    @Override
                    public TypedOutput toBody(Object object) {
                        return null;
                    }
                })
                .setEndpoint(baseUrl)
                .setClient(new OkClient(new OkHttpClient()));
        RestAdapter restAdapter = builder.build();
        return restAdapter.create(clazz);
    }
}
