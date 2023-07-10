package com.deloitte.ads.mariosy;

import java.util.ArrayList;
import java.util.Set;

public class User {
    private String username;
    private ArrayList<Marios> receivedMariosy;
    private ArrayList<Marios> givenMariosy;


    public User(String username) {
        this.username = username;
        this.receivedMariosy = new ArrayList<Marios>();
        this.givenMariosy = new ArrayList<Marios>();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ArrayList<Marios> getMariosy() {
        return receivedMariosy;
    }

    public ArrayList<Marios> getGivenMariosy() {
        return givenMariosy;
    }

    private void addMarios(Marios marios) {
        this.receivedMariosy.add(marios);
    }

    public void giveMarios(Marios marios, User user) {
        if (user == this)
            throw new IllegalArgumentException("You can not give marios to yourself");
        else {
            user.addMarios(marios);
            this.givenMariosy.add(marios);
        }
    }

    public void giveMarios(Marios marios, Set<User> users) {
        for (User u : users) {
            if (u == this)
                throw new IllegalArgumentException("You can not give marios to yourself");
            else {
                u.addMarios(marios);
            }
        }
        this.givenMariosy.add(marios);
    }
}
