package com.deloitte.ads.mariosy.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.google.common.collect.Sets;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity(name = "marios")
public class Marios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonIgnore
    private Long id;

    @Column(name = "external_id")
    private UUID externalId = UUID.randomUUID();

    @Column(name = "character_name")
    private String characterName;

    @Column(name = "comment")
    private String comment;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "externalId")
    @JsonIdentityReference(alwaysAsId = true)
    private User author;

    @ManyToMany(mappedBy = "receivedMarios")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "externalId")
    @JsonIdentityReference(alwaysAsId = true)
    private Set<User> receivers;

    public Marios(String characterName, String comment, User author) {
        this.characterName = characterName;
        this.comment = comment;
        this.author = author;
        this.receivers = Sets.newHashSet();
        author.giveMarios(this);
    }

    public Marios() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    @PreRemove
    public void removeReceivers() {
        for (User receiver : this.receivers) {
            receiver.getReceivedMarios().remove(this);
        }
    }

    public UUID getExternalId() {
        return externalId;
    }

    public void setExternalId(UUID externalId) {
        this.externalId = externalId;
    }
}
