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

    // temporary
    private static int UNIQUE_ID = 0;

    public static int getNextUniqueId() {
        UNIQUE_ID++;
        return UNIQUE_ID;
    }
    // ^ temporary

    private long id;
    private Character name;
    private String comment;
    private long authorId;
    private Set<Long> receiversIds;

    public Marios(Character name, String comment, long authorId, Set<Long> receiversIds) {
        this.id = getNextUniqueId();
        this.name = name;
        this.comment = comment;
        this.authorId = authorId;
        this.receiversIds = receiversIds;
    }

    public long getId() {
        return id;
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

    public long getAuthor() {
        return authorId;
    }

    public void setAuthor(Long authorId) {
        this.authorId = authorId;
    }

    public Set<Long> getReceiversIds() {
        return receiversIds;
    }

    public void setReceivers(Set<Long> receiversIds) {
        this.receiversIds = receiversIds;
    }

    @Override
    public String toString() {
        return name + ": " + comment + " ~" + authorId;
    }
}
