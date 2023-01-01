package com.example.atyourservice.Model;

public class JobSearch {
    String uid;
    Double latitude,longitude;

    public JobSearch(){}
    public JobSearch(String uid, Double latitude, Double longitude) {
        this.uid = uid;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
