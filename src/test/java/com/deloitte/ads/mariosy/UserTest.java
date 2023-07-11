package com.deloitte.ads.mariosy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserTest {

    private List<User> users;
    private Set<User> receivers;
    private List<Marios> mariosy;

    private User testUser;

    @BeforeEach
    void init() {
        testUser = new User("test");

        User user1 = new User("Mateusz");
        User user2 = new User("Bartek");
        User user3 = new User("Kamil");

        Marios Mario = new Marios(Marios.Character.MARIO, "test", testUser);
        Marios Bowser = new Marios(Marios.Character.BOWSER, "test123", testUser);
        Marios Daisy = new Marios(Marios.Character.DAISY, "test daisy", testUser);

        mariosy = List.of(Mario, Bowser, Daisy);
        users = List.of(user1, user2, user3);

        receivers = new HashSet<>();
        receivers.add(user2);
        receivers.add(user3);

    }

    @Test
    void getUsername() {
    }

    @Test
    void setUsername() {
    }

    @Test
    void getReceivedMariosy() {
        // given
        //when
        testUser.giveMarios(mariosy.get(0), users.get(0));
        //then
        Assertions.assertEquals(
                users.get(0).getReceivedMariosy().get(0),
                mariosy.get(0));
        Assertions.assertEquals(
                users.get(0).getReceivedMariosy().get(0),
                testUser.getGivenMariosy().get(0));

    }

    @Test
    void getGivenMariosy() {
        //when
        testUser.giveMarios(mariosy.get(1), users.get(1));
        //then
        Assertions.assertEquals(
                testUser.getGivenMariosy().get(0),
                mariosy.get(1));
        Assertions.assertEquals(
                testUser.getGivenMariosy().get(0),
                users.get(1).getReceivedMariosy().get(0));
    }

    @Test
    void giveMariosIllegalArgException() {
        //when
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            testUser.giveMarios(mariosy.get(0), testUser);
        });

        receivers.add(testUser);
        IllegalArgumentException thrownManyUsers = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            testUser.giveMarios(mariosy.get(0), receivers);
        });
        //then
        Assertions.assertEquals("You can not give marios to yourself", thrown.getMessage());
        Assertions.assertEquals("You can not give marios to yourself", thrownManyUsers.getMessage());
    }

    @Test
    void giveMariosNullPtrException() {
        // given
        Set<User> set = null;
        //when
        NullPointerException thrown = Assertions.assertThrows(NullPointerException.class, () -> {
            testUser.giveMarios(mariosy.get(0), set);
        });
        //then
        Assertions.assertEquals("No users provided", thrown.getMessage());
    }

    @Test
    void giveMariosManyUsers() {
        // when
        testUser.giveMarios(mariosy.get(2), receivers);
        // then
        Assertions.assertEquals(
                users.get(1).getReceivedMariosy().get(0),
                mariosy.get(2));
        Assertions.assertEquals(
                users.get(2).getReceivedMariosy().get(0),
                mariosy.get(2));
        Assertions.assertEquals(
                users.get(1).getReceivedMariosy().get(0),
                testUser.getGivenMariosy().get(0));
        Assertions.assertEquals(
                users.get(2).getReceivedMariosy().get(0),
                testUser.getGivenMariosy().get(0));
    }
}