package com.example.db2medicalinsurance;

import com.google.firebase.firestore.GeoPoint;

import java.util.HashMap;
import java.util.Map;

public class ProviderInfo {
    private Map<String, String> address = new HashMap();
    private String name;
    private String type;
    private String Owner;
    private String phone;
    private GeoPoint location;


    public ProviderInfo() {}

    public String getOwner() {
        return Owner;
    }

    public void setOwner(String owner) {
        Owner = owner;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public GeoPoint getLocation() {
        return location;
    }

    public void setLocation(GeoPoint location) {
        this.location = location;
    }

    public Map<String, String> getAddress() {
        return address;
    }

    public void setAddress(Map<String, String> address) {
        this.address = address;
    }
}