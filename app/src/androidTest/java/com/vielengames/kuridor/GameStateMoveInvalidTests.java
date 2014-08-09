package com.vielengames.kuridor;

import com.vielengames.data.kuridor.KuridorGameState;
import com.vielengames.data.kuridor.KuridorMove;

import junit.framework.TestCase;

public final class GameStateMoveInvalidTests extends TestCase {

    public void testShouldThrowExceptionOnInvalidMove() {
        KuridorGameState testedState = KuridorGameState.initial();
        try {
            testedState.move(KuridorMove.pawn("e5"));
            fail();
        } catch (IllegalStateException ignored) {
        }
    }
}
