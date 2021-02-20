package com.example.keepintouch.Model;

public class EmergencyItem {
    private int EmergencyImageResource;
    private String EmergencyName;
    private String EmergencyNumber;

    public EmergencyItem(int ImageResource, String Text1, String Text2) {
        EmergencyImageResource =ImageResource;
        EmergencyName=Text1;
        EmergencyNumber =Text2;
    }
    public int getEmergencyImageResource() {
        return EmergencyImageResource;
    }

    public void setEmergencyImageResource(int emergencyImageResource) {
        EmergencyImageResource = emergencyImageResource;
    }

    public void setEmergencyName(String emergencyName) {
        EmergencyName = emergencyName;
    }

    public void setEmergencyNumber(String emergencyNumber) {
        EmergencyNumber = emergencyNumber;
    }

    public String getEmergencyName(){
        return EmergencyName;
    }

    public String getEmergencyNumber(){
        return EmergencyNumber;
    }

}
