package com.example.keepintouch;

public class GroupItem {

    private String GroupName,Admin;

    public GroupItem(String groupName, String admin) {
        GroupName = groupName;
        Admin = admin;
    }

    public String getGroupName() {
        return GroupName;
    }

    public String getAdmin() {
        return Admin;
    }
}
