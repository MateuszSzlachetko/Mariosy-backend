package com.deloitte.ads.mariosy.entity;

import java.util.Set;

public class MariosDTO {
    private String characterName;
    private String comment;
    private long authorId;
    private Set<Long> receiversIds;

    public MariosDTO(String characterName, String comment, Long authorId, Set<Long> receiversIds) {
        if (characterName == null || comment == null || authorId == null || receiversIds == null)
            throw new IllegalArgumentException("Bad argument");

        this.characterName = characterName;
        this.comment = comment;
        this.authorId = authorId;
        this.receiversIds = receiversIds;
    }

    public String getCharacterName() {
        return characterName;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(long authorId) {
        this.authorId = authorId;
    }

    public Set<Long> getReceiversIds() {
        return receiversIds;
    }

    public void setReceiversIds(Set<Long> receiversIds) {
        this.receiversIds = receiversIds;
    }
}
