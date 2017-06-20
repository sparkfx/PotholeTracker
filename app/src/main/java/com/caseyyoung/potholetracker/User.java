package com.caseyyoung.potholetracker;

import java.util.ArrayList;

/**
 * Created by caseyyoung on 6/14/17.
 */

public class User {

    private static String emil;
    private int level;
    private int points;
    private String username;
    private String email;
    private ArrayList<Pothole> holes;

    public User (String email, ArrayList<Pothole> holes, int level, int points, String username){
        this.email = email;
        this.holes = holes;
        this.level = level;
        this.points = points;
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User(String email){
        this.emil = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public static String getEmil() {
        return emil;
    }

    public void setEmil(String email) {
        this.emil = email;
    }

    public void setHoles(ArrayList<Pothole> holes){
        this.holes = holes;
    }
    public ArrayList<Pothole> getHoles(){return holes;}

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
