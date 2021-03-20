package com.example.keepintouch.Model;

public class Message {
    private String text;
    private String name;
    private String time;

    public Message() {
    }

    public Message(String text, String name,String time) {
        this.text = text;
        this.name = name;
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
