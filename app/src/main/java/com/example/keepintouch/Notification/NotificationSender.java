package com.example.keepintouch.Notification;

import com.example.keepintouch.Notification.Data;

public class NotificationSender {
    private String to;
    private Data message;
    private Data notification;
    public NotificationSender(){
    }

    public NotificationSender( String to,Data data,Data data1) {
        this.to = to;
        this.message = data;
        this.notification = data1;
    }
}
