package com.caseyyoung.potholetracker;

/**
 * Created by caseyyoung on 5/5/17.
 */

public class Pothole {
    private int coords;
    private String descrip;

    public Pothole(int coords, String descrip){
        this.coords = coords;
        this.descrip = descrip;
    }

    public int getCoords() {
        return coords;
    }

    public void setCoords(int coords) {
        this.coords = coords;
    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }
}
