package com.deloitte.ads.mariosy.entity;

import java.util.Set;
import java.util.UUID;

public class MariosDTO {
    private String characterName;
    private String comment;
    private UUID authorId;
    private Set<UUID> receiversIds;

    public MariosDTO(String characterName, String comment, UUID authorId, Set<UUID> receiversIds) {
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

    public UUID getAuthorId() {
        return authorId;
    }

    public void setAuthorId(UUID authorId) {
        this.authorId = authorId;
    }

    public Set<UUID> getReceiversIds() {
        return receiversIds;
    }

    public void setReceiversIds(Set<UUID> receiversIds) {
        this.receiversIds = receiversIds;
    }
}
