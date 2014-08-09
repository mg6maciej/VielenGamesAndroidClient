package com.vielengames.kuridor;

import com.vielengames.data.kuridor.KuridorGame;
import com.vielengames.data.kuridor.KuridorMove;

import junit.framework.TestCase;

import java.util.Arrays;
import java.util.Collections;

public final class LastMoveStartPositionTests extends TestCase {

    public void testNoMoves() {
        KuridorGame game = KuridorGame.builder()
                .moves(Collections.<KuridorMove>emptyList())
                .build();
        String startPosition = game.getLastMoveStartPosition();
        assertNull(startPosition);
    }

    public void testWallMove() {
        KuridorGame game = KuridorGame.builder()
                .moves(Arrays.asList(KuridorMove.wall("e5h")))
                .build();
        String startPosition = game.getLastMoveStartPosition();
        assertEquals("e5h", startPosition);
    }

    public void testFewMoves() {
        KuridorGame game = KuridorGame.builder()
                .moves(Arrays.asList(KuridorMove.pawn("e2"), KuridorMove.pawn("e8"), KuridorMove.pawn("e3"), KuridorMove.pawn("e7")))
                .build();
        String startPosition = game.getLastMoveStartPosition();
        assertEquals("e8", startPosition);
    }

    public void testOneMove() {
        KuridorGame game = KuridorGame.builder()
                .moves(Arrays.asList(KuridorMove.pawn("e2")))
                .build();
        String startPosition = game.getLastMoveStartPosition();
        assertEquals("e1", startPosition);
    }

    public void testTwoMoves() {
        KuridorGame game = KuridorGame.builder()
                .moves(Arrays.asList(KuridorMove.pawn("e2"), KuridorMove.pawn("d9")))
                .build();
        String startPosition = game.getLastMoveStartPosition();
        assertEquals("e9", startPosition);
    }
}
