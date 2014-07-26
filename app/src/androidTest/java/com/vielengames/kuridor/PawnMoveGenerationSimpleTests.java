package com.vielengames.kuridor;

public final class PawnMoveGenerationSimpleTests extends PawnMoveGenerationBaseTestCase {

    public void testAllSimpleMovesValid() {
        testedState = withoutBlocks;
        assertContainsAll("e6", "f5", "e4", "d5");
    }
}
