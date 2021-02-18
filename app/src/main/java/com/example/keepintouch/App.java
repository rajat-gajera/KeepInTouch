package com.example.keepintouch;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import androidx.annotation.RequiresApi;

public class App extends Application {
    public static final String LOCATION_SERVICE_CHANNEL_ID = "LocationServiceChannel";


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate() {
        super.onCreate();
        CreateLocationChannel();


    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void CreateLocationChannel() {
    if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.O);
        {
            NotificationChannel serviceChannel =  new NotificationChannel(LOCATION_SERVICE_CHANNEL_ID,"Location Service Channel", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);

        }

    }

}
