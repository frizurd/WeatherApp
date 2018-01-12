package com.frizkykramer.weatherapp.view.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.frizkykramer.weatherapp.R;
import com.frizkykramer.weatherapp.environments.Variables;
import com.frizkykramer.weatherapp.libs.restrofit.RetrofitBuilder;
import com.frizkykramer.weatherapp.model.WeatherModel;
import com.frizkykramer.weatherapp.restclient.ApiInterface;
import com.squareup.picasso.Picasso;
import java.io.IOException;
import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {;

    @BindView(R.id.actMain_tvTitle)               TextView cityName;
    @BindView(R.id.actMain_tvHumidityNum)         TextView humidity;
    @BindView(R.id.actMain_tvTemperatureDegree)   TextView temperature;
    @BindView(R.id.actMain_tvWindSpeed)           TextView windSpeed;
    @BindView(R.id.actMain_tvWindDegree)          TextView windDegree;

    @BindView(R.id.actMain_ivWeather)             ImageView weatherImage;
    @BindView(R.id.actMain_ivHumidity)            ImageView humidityImage;
    @BindView(R.id.actMain_ivTemperature)         ImageView temperatureImage;
    @BindView(R.id.actMain_ivWind)                ImageView windImage;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle sideBarToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adaptDataAndViews();
    }

    private void adaptDataAndViews(){

        Toolbar toolBar = findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);
        drawerLayout = findViewById(R.id.drawer_layout);
        sideBarToggle = new ActionBarDrawerToggle(this, drawerLayout,  R.string.sideBarOpen, R.string.sideBarClose);
        drawerLayout.addDrawerListener(sideBarToggle);
        sideBarToggle.syncState();

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        createWeatherApi();

        ButterKnife.bind(this);

        Picasso.with(this).load(R.drawable.raindrops).into(humidityImage);
        Picasso.with(this).load(R.drawable.temperature2).into(temperatureImage);
        Picasso.with(this).load(R.drawable.wind).into(windImage);

        cityName.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, LocationActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });
    }

    public void createWeatherApi() {

        ApiInterface apiService = RetrofitBuilder.getInstance().getRetrofit().create(ApiInterface.class);

        String city = "London,uk";

        Call<WeatherModel> call = apiService.getWeather(city);
        call.enqueue(new Callback<WeatherModel>() {
            @Override
            public void onResponse(Call<WeatherModel> call, Response<WeatherModel> response) {
                int statusCode = response.code();
                WeatherModel weather = response.body();

                // Check if the city has been changed through a popup
                if (getIntent().getExtras() != null && getIntent().getStringExtra("cityName") != null) {
                    cityName.setText(getIntent().getStringExtra("cityName"));
                } else {
                    cityName.setText(weather.getCityName());
                }

                humidity.setText(String.valueOf(weather.getHumidity()));
                temperature.setText(String.valueOf(weather.getTemp() + " °F"));
                windSpeed.setText(String.valueOf(weather.getWindspeed() + "d "));
                windDegree.setText(String.valueOf(weather.getWindDeg() + " deg"));

                switchWeatherImage(weather.getWeatherType());
            }

            @Override
            public void onFailure(Call<WeatherModel> call, Throwable t) {
            }
        });
    }

    public void switchWeatherImage(String weather) {

        switch (weather) {
            case "Rainy":
                Picasso.with(this).load(R.drawable.rainy).into(weatherImage);
                break;
            case "Drizzle":
                Picasso.with(this).load(R.drawable.raining).into(weatherImage);
                break;
            case "Cloudy":
                Picasso.with(this).load(R.drawable.clouds).into(weatherImage);
                break;
            case "Sunny":
                Picasso.with(this).load(R.drawable.sunny).into(weatherImage);
                break;
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
