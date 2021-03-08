package com.example.keepintouch;

public class Post {

    String id;
    String imgURL;
    String caption;
    String userName;
    String userId;
    String date;
    String time;
    String code;

    public Post(String id, String imgURL, String caption, String userName, String userId, String date, String time, String code) {
        this.id = id;
        this.imgURL = imgURL;
        this.caption = caption;
        this.userName = userName;
        this.userId = userId;
        this.date = date;
        this.time = time;
        this.code = code;
    }

    public Post(){}

    public String getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
