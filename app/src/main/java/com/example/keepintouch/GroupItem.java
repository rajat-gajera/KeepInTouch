package com.example.keepintouch;

import java.util.ArrayList;

public class GroupItem {

    private String GroupName, AdminId, AdminEmail, Code, Radius,GroupId;

    // private ArrayList<String> MemberList;GroupId
    public GroupItem() {

    }

    public GroupItem(String groupName,String groupId, String adminId, String adminEmail, String code, String radius) {
          GroupId = groupId;
        GroupName = groupName;
        AdminId = adminId;
        AdminEmail = adminEmail;
        Code = code;
        Radius = radius;
//        MemberList = memberList;
    }

    public String getGroupId() {
        return GroupId;
    }

    public void setGroupId(String groupId) {
        GroupId = groupId;
    }

    public String getAdminEmail() {
        return AdminEmail;
    }

    public void setAdminName(String adminEmail) {
        AdminEmail = adminEmail;
    }

    public String getGroupName() {
        return GroupName;
    }

    public void setGroupName(String groupName) {
        GroupName = groupName;
    }

    public String getAdminId() {
        return AdminId;
    }

    public void setAdminId(String adminId) {
        AdminId = adminId;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getRadius() {
        return Radius;
    }

    public void setRadius(String radius) {
        Radius = radius;
    }

//    public ArrayList<String> getMemberList() {
//        return MemberList;
//    }


}
