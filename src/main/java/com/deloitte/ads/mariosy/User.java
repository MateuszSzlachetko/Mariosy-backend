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

    public ArrayList<Marios> getReceivedMariosy() {
        return receivedMariosy;
    }

    public ArrayList<Marios> getGivenMariosy() {
        return givenMariosy;
    }

    private void addMarios(Marios marios) {
        this.receivedMariosy.add(marios);
    }

    public void giveMarios(Marios marios, User user) throws IllegalArgumentException {
        if (user == this)
            throw new IllegalArgumentException("You can not give marios to yourself");

        user.addMarios(marios);
        this.givenMariosy.add(marios);
    }

    public void giveMarios(Marios marios, Set<User> users) throws NullPointerException, IllegalArgumentException {
        if (users == null)
            throw new NullPointerException("No users provided");

        if (users.contains(this))
            throw new IllegalArgumentException("You can not give marios to yourself");

        for (User u : users) {
            u.addMarios(marios);
        }
        this.givenMariosy.add(marios);
    }
}
