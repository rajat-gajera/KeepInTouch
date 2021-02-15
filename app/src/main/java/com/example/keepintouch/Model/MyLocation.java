package com.example.keepintouch.Model;

import android.location.Location;

public class MyLocation {

    String userId;
    Double latitude;
    Double longitude;
    long time;

    public MyLocation() {
    }

    public MyLocation(String userId, Location l) {
        latitude = l.getLatitude();
        longitude = l.getLongitude();
        time = l.getTime();
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
