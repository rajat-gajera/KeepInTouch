package com.example.keepintouch.Model;

import java.util.ArrayList;

public class User {

    private String UserId,Name,Phone,Email,Password,Date,Time,Latitude,Logitude;
    private ArrayList<String> GroupCode;
    public User() {
    }

    public User(String userId, String name, String phone, String email, String password, String date, String time, String latitude, String logitude) {
        UserId = userId;
        Name = name;
        Phone = phone;
        Email = email;
        Password = password;
        Date = date;
        Time = time;
        Latitude = latitude;
        Logitude = logitude;
        //GroupCode = new ArrayList<>();
    }
//    public void setGroupCode(String code)
//    {
//        GroupCode.add(code);
//    }
//    public ArrayList<String> getGroupCode()
//    {
//        return GroupCode;
//    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getLogitude() {
        return Logitude;
    }

    public void setLogitude(String logitude) {
        Logitude = logitude;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
