package com.caseyyoung.potholetracker;

/**
 * Created by caseyyoung on 5/5/17.
 */

import com.google.android.gms.maps.model.LatLng;

public class Pothole {
    private LatLng coords;
    private String descrip;

    public Pothole(){

    }
    public Pothole(LatLng coords, String descrip){
        this.coords = coords;
        this.descrip = descrip;
    }

    public LatLng getCoords() {
        return coords;
    }

    public void setCoords(LatLng coords) {
        this.coords = coords;
    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }
}
