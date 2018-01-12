package com.frizkykramer.weatherapp.view.activity;

import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.frizkykramer.weatherapp.R;
import com.frizkykramer.weatherapp.adapter.MyRecyclerViewAdapter;
import com.frizkykramer.weatherapp.application.WeatherReceiver;
import com.frizkykramer.weatherapp.environments.SimpleDividerItemDecoration;

import java.util.ArrayList;

public class LocationActivity extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener {

    MyRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.change_locaten_title);

        ArrayList<String> cityNames = new ArrayList<>();
        cityNames.add("New York");
        cityNames.add("London");
        cityNames.add("Amsterdam");
        cityNames.add("Jakarta");
        cityNames.add("Seoul");
        cityNames.add("Bangkok");
        cityNames.add("Tokyo");
        cityNames.add("Singapore");
        cityNames.add("Kuala Lumpur");

        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.rvCities);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyRecyclerViewAdapter(this, cityNames);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(
                getApplicationContext()
        ));

    }

    @Override
    public void onItemClick(View view, int position) {

        Intent intent = new Intent(this, WeatherReceiver.class);
        intent.putExtra("cityName", adapter.getItem(position));
        sendBroadcast(intent);

    }
}
