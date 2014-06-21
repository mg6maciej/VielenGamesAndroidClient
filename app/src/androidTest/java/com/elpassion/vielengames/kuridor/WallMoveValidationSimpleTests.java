package com.elpassion.vielengames.kuridor;

import com.elpassion.vielengames.data.kuridor.KuridorGameState;

public final class WallMoveValidationSimpleTests extends WallMoveValidationBaseTestCase {

    public void testSimpleMovesAreValid() {
        testedState = KuridorGameState.initial();
        assertValid("a1v");
        assertValid("a8h");
        assertValid("h1h");
        assertValid("h8v");
    }

    public void testWeirdMovesAreNotValid() {
        testedState = KuridorGameState.initial();
        assertNotValid("");
        assertNotValid("f");
        assertNotValid("8");
        assertNotValid("test");
        assertNotValid("1a");
        assertNotValid("j5");
        assertNotValid("gg");
        assertNotValid("c10");
        assertNotValid("d0");
        assertNotValid("66");
        assertNotValid("a5");
        assertNotValid("c8");
        assertNotValid("a9v");
        assertNotValid("i4h");
        assertNotValid("i5v");
    }
}
