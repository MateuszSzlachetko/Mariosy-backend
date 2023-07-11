package com.deloitte.ads.mariosy.repository;

public enum Character {
    MARIO("Mario"),
    LUIGI("Luigi"),
    BOWSER("Bowser"),
    YOSHI("Yoshi"),
    ROSALINA("Rosalina"),
    DAISY("Daisy");

    private String name;

    private Character(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
