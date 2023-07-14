package com.deloitte.ads.mariosy.entity;

public class UserDTO {
    private String userName;
    private String email;

    public UserDTO(String userName, String email) {
        if (userName == null || email == null)
            throw new IllegalArgumentException("Bad argument");

        this.userName = userName;
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
