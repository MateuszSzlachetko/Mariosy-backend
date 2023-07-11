package com.deloitte.ads.mariosy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MariosTest {
    private Marios marios;

    @BeforeEach
    void init() {
        marios = new Marios(Marios.Character.ROSALINA, "test", new User("Test"));
    }

    @Test
    void getName() {
        Marios.Character name = marios.getName();

        Assertions.assertEquals(name.toString(), "Rosalina");
    }

    @Test
    void setName() {
        marios.setName(Marios.Character.MARIO);
        Marios.Character name = marios.getName();

        Assertions.assertNotEquals(name.toString(), "Rosalina");
        Assertions.assertEquals(name.toString(), "Mario");
    }

    @Test
    void getComment() {
        String comment = marios.getComment();

        Assertions.assertEquals(comment.toString(), "test");
    }

    @Test
    void setComment() {
        marios.setComment("Nice one");
        String comment = marios.getComment();

        Assertions.assertNotEquals(comment.toString(), "test");
        Assertions.assertEquals(comment.toString(), "Nice one");
    }
    

    @Test
    void testToString() {
        //given
        User user = new User("Test");
        Marios Mario = new Marios(Marios.Character.MARIO, "test", user);
        // when
        String mariosString = Mario.toString();
        // then
        Assertions.assertEquals(mariosString, "Mario: test ~Test");
    }
}