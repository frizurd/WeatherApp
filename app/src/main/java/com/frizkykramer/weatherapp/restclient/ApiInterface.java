package com.frizkykramer.weatherapp.restclient;

import android.accounts.Account;

import com.frizkykramer.weatherapp.model.AccountModel;
import com.frizkykramer.weatherapp.model.WeatherModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface
{

    @GET("weather")
    Call<WeatherModel> getWeather(
            @Query("q") String city
    );

    @POST("sms")
    @FormUrlEncoded
    Call<AccountModel> verifyPhone(
            @Field("phone") String phone,
            @Field("forUpdate") String update
    );

}