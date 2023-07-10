package com.deloitte.ads;

import com.deloitte.ads.mariosy.Marios;
import com.deloitte.ads.mariosy.User;

import java.util.HashSet;
import java.util.Set;

public class Main {
    private static Set<User> users = new HashSet<User>();

    public static Set<User> getUsers() {
        return users;
    }

    public static void main(String[] args) {
        User user1 = new User("Mateusz");
        User user2 = new User("Bartek");
        User user3 = new User("Kamil");

        users.add(user1);
        users.add(user2);
        users.add(user3);

        Marios marios1 = new Marios(Marios.Character.MARIO, "test", user1);
        Marios marios2 = new Marios(Marios.Character.BOWSER, "test123", user1);
        Marios marios3 = new Marios(Marios.Character.DAISY, "test daisy", user1);

        Set<User> set = new HashSet<>();

        set.add(user2);
        set.add(user3);

        user1.giveMarios(marios1, user2);
        user1.giveMarios(marios3, user3);
        user1.giveMarios(marios2, set);

        System.out.println("Given mariosy");
        for (User u : users) {
            System.out.println(u.getGivenMariosy());
        }

        System.out.println("Users mariosy");
        for (User u : users) {
            System.out.println(u.getMariosy());
        }

    }

}
