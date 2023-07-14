package com.deloitte.ads.mariosy.repository;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "MARIOS")
public class Marios {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "character_name")
    private String characterName;

    @Column(name = "comment")
    private String comment;

    @Column(name = "author_id")
    private long authorId;
    //private Set<Long> receiversIds;

    public Marios() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    @Override
    public String toString() {
        return characterName + ": " + comment + " ~" + authorId;
    }
}
