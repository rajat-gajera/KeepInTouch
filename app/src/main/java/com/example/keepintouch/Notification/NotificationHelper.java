package com.example.keepintouch.Notification;

import android.content.Context;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.keepintouch.MainActivity;
import com.example.keepintouch.R;

public class NotificationHelper {


    private static final String TAG = "not_help_tager";

    public static void displayNotification(Context context,String title,String body) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, MainActivity.ALERT_SERVICE_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_android_official)
                .setContentTitle(title)
                .setContentText( body + "")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat mNotificationManagerCompat = NotificationManagerCompat.from(context);
        mNotificationManagerCompat.notify(2, mBuilder.build());
    }

}
