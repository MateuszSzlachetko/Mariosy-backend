package com.deloitte.ads.mariosy.entity;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.google.common.collect.Sets;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

@Entity(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonIgnore
    private Long id;

    @Column(name = "external_id")
    private UUID externalId;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
            name = "marios_receivers",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "marios_id"))
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "externalId")
    @JsonIdentityReference(alwaysAsId = true)
    private Set<Marios> receivedMarios;

    @OneToMany(mappedBy = "author", orphanRemoval = true)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "externalId")
    @JsonIdentityReference(alwaysAsId = true)
    private Set<Marios> givenMarios;

    public User(String username, String email, UUID externalId) {
        this.username = username;
        this.email = email;
        this.externalId = externalId;
        this.receivedMarios = Sets.newHashSet();
        this.givenMarios = Sets.newHashSet();
    }

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Marios> getReceivedMarios() {
        return receivedMarios;
    }

    public void setReceivedMarios(Set<Marios> receivedMarios) {
        this.receivedMarios = receivedMarios;
    }

    public Set<Marios> getGivenMarios() {
        return givenMarios;
    }

    public void setGivenMarios(Set<Marios> givenMarios) {
        this.givenMarios = givenMarios;
    }

    public void addMarios(Marios marios) {
        this.receivedMarios.add(marios);
        marios.getReceivers().add(this);
    }

    public void removeMarios(Marios marios) {
        this.receivedMarios.remove(marios);
        marios.getReceivers().remove(this);
    }

    @PreRemove
    public void removeGivenMarios() {
        for (Iterator<Marios> i = givenMarios.iterator(); i.hasNext(); ) {
            Marios marios = i.next();
            i.remove();
        }
    }

    public UUID getExternalId() {
        return externalId;
    }

    public void setExternalId(UUID externalId) {
        this.externalId = externalId;
    }

    public void giveMarios(Marios marios) {
        this.givenMarios.add(marios);
    }
}

