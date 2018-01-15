package com.frizkykramer.weatherapp.libs.libs.Restrofit;

import com.frizkykramer.weatherapp.environments.Variables;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitBuilderOKHome {

    private static RetrofitBuilderOKHome instance = null;
    private static Retrofit retrofit;

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
    public static final Retrofit getRetrofit() {
        return retrofit;
    }
}
