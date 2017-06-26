package com.caseyyoung.potholetracker;

/**
 * Created by caseyyoung on 5/5/17.
 */

import com.google.android.gms.maps.model.LatLng;

public class Pothole {
    private LatLng coords;
    private double lat;
    private double lng;

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    private String address;
    private int severity;
    private final int MAX_SEVERITY =5;
    private final int MIN_SEVERITY =1;
    public Pothole(){
    }
    public Pothole(double lat, double lng, String address, int severity){
        this.lat = lat;
        this.lng = lng;
        this.address = address;
        this.severity =severity;
        coords = new LatLng(lat,lng);
    }

    public int getSeverity() {
        return severity;
    }

    public void setSeverity(int severity) {
        if(severity <= MAX_SEVERITY && severity >= MIN_SEVERITY)
        this.severity = severity;
        else if(severity > MAX_SEVERITY)
            this.severity = MAX_SEVERITY;
        else
            this.severity=MIN_SEVERITY;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String toString(){
        return this.lat + ", " + this.lng + "\n" + this.address + "\n" + this.severity;

    }
}
