package com.vielengames.kuridor;

import com.vielengames.data.kuridor.KuridorGameState;
import com.vielengames.data.kuridor.KuridorMove;

import junit.framework.TestCase;

public abstract class WallMoveValidationBaseTestCase extends TestCase {

    KuridorGameState testedState;

    boolean valid(String position) {
        return testedState.isMoveValid(KuridorMove.wall(position));
    }

    void assertValid(String position) {
        assertTrue(valid(position));
    }

    void assertNotValid(String position) {
        assertFalse(valid(position));
    }
}
