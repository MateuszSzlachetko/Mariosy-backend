package com.deloitte.ads.mariosy.repository;

import java.util.ArrayList;
import java.util.Set;

public class User {
    private long id;
    private String username;
    private ArrayList<Marios> receivedMariosy;
    private ArrayList<Marios> givenMariosy;


    public User(long id,String username) {
        this.id=id;
        this.username = username;
        this.receivedMariosy = new ArrayList<Marios>();
        this.givenMariosy = new ArrayList<Marios>();
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ArrayList<Marios> getReceivedMariosy() {
        return receivedMariosy;
    }

    public ArrayList<Marios> getGivenMariosy() {
        return givenMariosy;
    }

    private void addMarios(Marios marios) {
        this.receivedMariosy.add(marios);
    }

    private void giveMarios(Marios marios) {
        this.givenMariosy.add(marios);
    }

}

