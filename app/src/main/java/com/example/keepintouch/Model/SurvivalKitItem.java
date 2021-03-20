package com.example.keepintouch.Model;


public class SurvivalKitItem {
    private int ImageSource;
    private String mText1;
    private  String mText2;

    public SurvivalKitItem(int imageSource, String mText1, String mText2) {
        ImageSource = imageSource;
        this.mText1 = mText1;
        this.mText2 = mText2;
    }

    public int getImageSource() {
        return ImageSource;
    }

    public String getmText1() {
        return mText1;
    }

    public String getmText2() {
        return mText2;
    }
}
