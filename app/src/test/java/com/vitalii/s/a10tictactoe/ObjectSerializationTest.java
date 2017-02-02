package com.vitalii.s.a10tictactoe;

import com.google.gson.Gson;

import org.junit.Before;
import org.junit.Test;

import playground.Seed;
import playground.SimplePlayGround;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ObjectSerializationTest {

    SimplePlayGround simplePlayGround=new SimplePlayGround();
    @Before
    public void init() {

        simplePlayGround.start();
        simplePlayGround.getBoard().cells[1][1].content= Seed.CROSS;
    }

    @Test
    public void doesGsonWork() throws Exception {

        String gSon = new Gson().toJson(simplePlayGround);
        SimplePlayGround newSimplePlayGround = new SimplePlayGround();
        newSimplePlayGround = new Gson().fromJson(gSon, SimplePlayGround.class);
        assertEquals(newSimplePlayGround.getBoard().cells[1][1].content, Seed.CROSS);
    }
}