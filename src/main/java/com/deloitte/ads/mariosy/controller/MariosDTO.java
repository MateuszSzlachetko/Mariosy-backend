package com.deloitte.ads.mariosy.controller;

import com.deloitte.ads.mariosy.repository.Character;

import java.util.Set;

public class MariosDTO {

    private Set<Long> receiversIds;
    private long authorId;
    private Character name;
    private String comment;

    public Set<Long> getReceiversIds() {
        return receiversIds;
    }

    public void setReceiversIds(Set<Long> receiversIds) {
        this.receiversIds = receiversIds;
    }

    public long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(long authorId) {
        this.authorId = authorId;
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
}
