package com.caseyyoung.potholetracker;

/**
 * Created by caseyyoung on 5/5/17.
 */

import com.google.android.gms.maps.model.LatLng;

public class Pothole {
    private LatLng coords;
    private String address;
    private int severity;
    private final int MAX_SEVERITY =5;
    private final int MIN_SEVERITY =1;
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
        if(severity < MAX_SEVERITY && severity > MIN_SEVERITY)
        this.severity = severity;
        else if(severity > MAX_SEVERITY)
            this.severity = MAX_SEVERITY;
        else
            this.severity=MIN_SEVERITY;
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

    public void setAddress(String address) {
        this.address = address;
    }

    public String toString(){
        return
//                this.coords + "\t" +
                this.address + "\t" + this.severity;
    }
}
