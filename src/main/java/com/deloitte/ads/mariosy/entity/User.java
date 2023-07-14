package com.deloitte.ads.mariosy.entity;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.google.common.collect.Sets;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
            name = "marios_receivers",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "marios_id"))
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private Set<Marios> receivedMarios;

    @OneToMany(mappedBy = "author")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private Set<Marios> givenMarios;

    public User(String username, String email) {
        this.username = username;
        this.email = email;
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
}

