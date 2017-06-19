package com.caseyyoung.potholetracker;

/**
 * Created by caseyyoung on 5/5/17.
 */

import com.google.android.gms.maps.model.LatLng;

public class Pothole {
    private LatLng coords;
    private String address;
    private int severity;
    public Pothole(){

    }
    public Pothole(LatLng coords, String address, int severity){
        this.coords = coords;
        this.address = address;
        this.severity =severity;
    }

    public int getSeverity() {
        return severity;
    }

    public void setSeverity(int severity) {
        this.severity = severity;
    }

    public LatLng getCoords() {
        return coords;
    }

    public void setCoords(LatLng coords) {
        this.coords = coords;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String descrip) {
        this.address = address;
    }

    public String toString(){
        return this.coords + "\n" + this.address + "\n" + this.severity;
    }
}
