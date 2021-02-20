package com.example.keepintouch;

import android.content.Context;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class NotificationHelper {


    public static void displayNotification(Context context) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, MainActivity.ALERT_SERVICE_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_android)
                .setContentTitle("KeepInTouch")
                .setContentText("You are Out Of Zone")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat mNotificationManagerCompat = NotificationManagerCompat.from(context);
        mNotificationManagerCompat.notify(2, mBuilder.build());
    }
}
