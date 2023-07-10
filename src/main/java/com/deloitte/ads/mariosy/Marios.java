package com.deloitte.ads.mariosy;

public class Marios {
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

    private Character name;
    private String comment;
    private User author;

    public Marios(Character name, String comment, User author) {
        this.name = name;
        this.comment = comment;
        this.author = author;
    }


    public Character getName() {
        return name;
    }

    public void setName(Character name) {
        this.name = name;
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

    @Override
    public String toString() {
        return name + ": " + comment + " ~" + author.getUsername();
    }
}
