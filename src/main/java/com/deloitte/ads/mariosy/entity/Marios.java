package com.deloitte.ads.mariosy.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.google.common.collect.Sets;

import javax.persistence.*;
import java.time.Instant;
import java.util.Date;
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

    @Column(name = "title")
    private String title;


    @Column(name = "created_at")
    private Date creationDate;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "externalId")
    @JsonIdentityReference(alwaysAsId = true)
    private User author;

    @Column(name = "author_username")
    private String authorUsername;

    @ManyToMany(mappedBy = "receivedMarios")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "externalId")
    @JsonIdentityReference(alwaysAsId = true)
    private Set<User> receivers;

    public Marios(String characterName, String comment, User author, String title) {
        this.characterName = characterName;
        this.comment = comment;
        this.author = author;
        this.authorUsername = author.getUsername();
        this.title = title;
        this.receivers = Sets.newHashSet();
        this.creationDate = new Date();
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

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthorUsername() {
        return authorUsername;
    }

    public void setAuthorUsername(String authorUsername) {
        this.authorUsername = authorUsername;
    }
}
