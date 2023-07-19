package com.deloitte.ads.mariosy.entity;

public class UserDTO {
    private String username;
    private String email;

    public UserDTO(String username, String email) {
        if (username == null || email == null)
            throw new IllegalArgumentException("Bad argument");

        this.username = username;
        this.email = email;
    }

    public String getUserName() {
        return username;
    }

    public void setUserName(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
