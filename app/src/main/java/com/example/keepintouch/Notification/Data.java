package com.example.keepintouch.Notification;

public class Data {
    String title;
    String body;

    public Data(String title) {
        this.title = title;
    }

    public Data(String title, String message) {
        this.title = title;
        this.body = message;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return body;
    }

    public void setMessage(String message) {
        this.body = message;
    }
}
