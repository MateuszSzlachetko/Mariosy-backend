package com.deloitte.ads.mariosy.repository;
// TODO
//DONE USER, MARIOS, CHARACTER do encji, id
// MARIOService do obsługi logiki dodawnia mariosów itd.
//DONE UserService do obsługi pobierania userów, dodawania userów, pobierania ich mariosów itd
// UserController do widoków usera
// MariosController do dodawania mariosów

import java.util.HashSet;
import java.util.Set;

public class Marios {
    private long id;
    private Character name;
    private String comment;
    private User author;
    private Set<User> receivers;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Marios(long id, Character name, String comment, User author) {
        this.id = id;
        this.name = name;
        this.comment = comment;
        this.author = author;
        this.receivers = new HashSet<>();
    }


    public Character getName() {
        return name;
    }

    public void setName(Character name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Set<User> getReceivers() {
        return receivers;
    }

    public void setReceivers(Set<User> receivers) {
        this.receivers = receivers;
    }

    @Override
    public String toString() {
        return name + ": " + comment + " ~" + author.getUsername();
    }
}
