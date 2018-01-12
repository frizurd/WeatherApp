package com.frizkykramer.weatherapp.application;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.frizkykramer.weatherapp.R;
import com.frizkykramer.weatherapp.view.activity.MainActivity;

public class WeatherReceiver extends BroadcastReceiver {

    private String cityName;
    private Context context;
    private Intent intent;

    public WeatherReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        this.intent = intent;
        cityName = intent.getStringExtra("cityName");
        callNotification();
    }

    private void callNotification()
    {
        Intent in = new Intent(context, MainActivity.class);
        in.putExtra("cityName", cityName);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0 , in
                ,PendingIntent.FLAG_CANCEL_CURRENT | PendingIntent.FLAG_ONE_SHOT);

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.bolt)
                .setContentText("Your location has been changed, click here for the weather of "+ cityName)
                .setTicker("Location changed: " + cityName)
                .setAutoCancel(true)
                .setContentTitle("Location changed: " + cityName)
                .setPriority(Notification.PRIORITY_MAX)
                .setDefaults(NotificationCompat.DEFAULT_VIBRATE)
                .setContentIntent(pendingIntent);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "WEATHER_CHANNEL_1";
            CharSequence name = "WeatherChannel";

            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(channelId, name, importance);
            notificationManager.createNotificationChannel(mChannel);
            notificationBuilder.setChannelId(channelId);
        }

        int id = 1;

        notificationManager.notify(id, notificationBuilder.build());
    }
}
