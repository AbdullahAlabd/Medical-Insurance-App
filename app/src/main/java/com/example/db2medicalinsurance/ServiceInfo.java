package com.example.db2medicalinsurance;
import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.GeoPoint;

import java.util.Date;

public class ServiceInfo {

    private Date start_time;
    private Date end_time;
    private String provider_type;
    private GeoPoint provider_location;
    private String description;
    private String name;
    private String provider_name;
    private String provider_id;
    private String documentId;

    @Exclude
    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }



    public ServiceInfo(Date start_time, Date end_time, String provider_type, GeoPoint provider_location, String description, String name, String provider_name , String provider_id) {
        this.start_time = start_time;
        this.end_time = end_time;
        this.provider_type = provider_type;
        this.provider_location = provider_location;
        this.description = description;
        this.name = name;
        this.provider_name = provider_name;
        this.provider_id = provider_id;
        this.documentId = documentId;
    }

    public Date getStart_time() {
        return start_time;
    }

    public void setStart_time(Date start_time) {
        this.start_time = start_time;
    }

    public Date getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
    }

    public String getProvider_type() {
        return provider_type;
    }

    public void setProvider_type(String provider_type) {
        this.provider_type = provider_type;
    }

    public GeoPoint getProvider_location() {
        return provider_location;
    }

    public void setProvider_location(GeoPoint provider_location) {
        this.provider_location = provider_location;
    }


    public ServiceInfo(){

    }

    /*public ServiceInfo(String description, String name, String provider_id, String provider_name) {
        this.description = description;
        this.name = name;
        this.provider_id = provider_id;
        this.provider_name = provider_name;
    }*/

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProvider_name() {
        return provider_name;
    }

    public void setProvider_name(String provider_name) {
        this.provider_name = provider_name;
    }

    public String getProvider_id() {
        return provider_id;
    }

    public void setProvider_id(String provider_id) {
        this.provider_id = provider_id;
    }
}