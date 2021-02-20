package com.example.keepintouch.Model;

import android.location.Location;

public class MyLocation {

    String userId;
    Double latitude;
    Double longitude;
    long time;
    boolean isSafe;
    public MyLocation() {
    }

    public MyLocation(String userId, Location l,boolean isSafe) {
        latitude = l.getLatitude();
        longitude = l.getLongitude();
        time = l.getTime();
        this.userId = userId;
        this.isSafe = isSafe;
    }

    public boolean isSafe() {
        return isSafe;
    }

    public void setSafe(boolean safe) {
        isSafe = safe;
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
