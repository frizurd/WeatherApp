package com.frizkykramer.weatherapp.libs.restrofit;

import com.frizkykramer.weatherapp.environments.Variables;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitBuilderOKHome {

    private static RetrofitBuilderOKHome instance = null;
    private Retrofit retrofit;

    public RetrofitBuilderOKHome() {

        retrofit = new Retrofit.Builder()
                .baseUrl(Variables.OKHOME_PHONE_VERIFY_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    public static RetrofitBuilderOKHome getInstance() {
        if (instance == null) {
            instance = new RetrofitBuilderOKHome();
        }
        return instance;
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }
}
