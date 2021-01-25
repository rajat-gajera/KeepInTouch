package com.example.keepintouch;

import java.util.ArrayList;

public class Zone {
    String groupId,secretcode,latitude,longitude,radius;
    ArrayList<String> memberList;

    public Zone(String groupId, String secretcode, String latitude, String longitude, String radius, ArrayList<String> codeList) {
        this.groupId = groupId;
        this.secretcode = secretcode;
        this.latitude = latitude;
        this.longitude = longitude;
        this.radius = radius;
        this.memberList = codeList;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getSecretcode() {
        return secretcode;
    }

    public void setSecretcode(String secretcode) {
        this.secretcode = secretcode;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getRadius() {
        return radius;
    }

    public void setRadius(String radius) {
        this.radius = radius;
    }

    public ArrayList<String> getMemberList() {
        return memberList;
    }

    public void setMemberList(ArrayList<String> memberList) {
        this.memberList = memberList;
    }
}
