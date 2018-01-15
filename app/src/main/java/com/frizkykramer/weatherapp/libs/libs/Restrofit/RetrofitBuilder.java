package com.frizkykramer.weatherapp.libs.libs.Restrofit;

import com.frizkykramer.weatherapp.environments.Variables;
import java.io.IOException;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitBuilder {

    private final String apiKey = "cbb9e8a348d065509f07a55572050115";

    private static RetrofitBuilder instance = null;
    private static Retrofit retrofit;
    private static OkHttpClient client;

    public RetrofitBuilder() {

        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();

        okHttpBuilder.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                HttpUrl httpUrl = original.url();
                HttpUrl newHttpUrl = httpUrl.newBuilder().addQueryParameter("appid", apiKey).build();

                Request.Builder requestBuilder = original.newBuilder().url(newHttpUrl);
                Request request = requestBuilder.build();

                return chain.proceed(request);
            }
        });

        client = okHttpBuilder.build();

        retrofit = new Retrofit.Builder()
                .baseUrl(Variables.API_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    public static final RetrofitBuilder getInstance() {
        if (instance == null) {
            instance = new RetrofitBuilder();
        }
        return instance;
    }

    public static final Retrofit getRetrofit() {
            return getInstance().retrofit;
    }
}
