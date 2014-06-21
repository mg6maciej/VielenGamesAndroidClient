package com.elpassion.vielengames.kuridor;

import com.elpassion.vielengames.data.kuridor.KuridorGameState;

import java.util.Collections;

public final class PawnMoveValidationSimpleTests extends PawnMoveValidationBaseTestCase {

    public void testWeirdMovesAreNotValid() {
        testedState = withoutBlocks;
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
        assertNotValid("d4h");
        assertNotValid("d4v");
    }

    public void testSimpleMovesAreValid() {
        testedState = withoutBlocks;
        assertValid("e4");
        assertValid("d5");
        assertValid("f5");
        assertValid("e6");
    }

    public void testFarMovesAreNotValid() {
        testedState = withoutBlocks;
        assertNotValid("a1");
        assertNotValid("a9");
        assertNotValid("i1");
        assertNotValid("i9");
    }

    public void testNotSoNearMovesAreNotValid() {
        testedState = withoutBlocks;
        assertNotValid("e7");
        assertNotValid("f6");
        assertNotValid("g5");
        assertNotValid("f4");
        assertNotValid("e3");
        assertNotValid("d4");
        assertNotValid("c5");
        assertNotValid("d6");
    }

    public void testZeroLengthMoveIsNotValid() {
        testedState = withoutBlocks;
        assertNotValid("e5");
    }

    private KuridorGameState withoutBlocks = KuridorGameState.builder()
            .team1(centered)
            .team2(secondStarting)
            .walls(Collections.<String>emptyList())
            .activeTeam("team_1")
            .build();
}
