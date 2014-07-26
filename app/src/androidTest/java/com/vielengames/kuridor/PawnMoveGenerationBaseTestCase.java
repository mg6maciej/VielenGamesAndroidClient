package com.vielengames.kuridor;

import com.vielengames.data.kuridor.KuridorGameState;
import com.vielengames.data.kuridor.KuridorGameTeamState;
import com.vielengames.data.kuridor.KuridorMove;

import junit.framework.TestCase;

import java.util.Collection;
import java.util.HashSet;

public abstract class PawnMoveGenerationBaseTestCase extends TestCase {

    KuridorGameState testedState;

    KuridorGameTeamState centered = KuridorGameTeamState.builder()
            .pawnPosition("e5")
            .wallsLeft(10)
            .build();
    KuridorGameTeamState secondStarting = KuridorGameTeamState.builder()
            .pawnPosition("e9")
            .wallsLeft(10)
            .build();
    KuridorGameTeamState northFromCenter = KuridorGameTeamState.builder()
            .pawnPosition("e6")
            .wallsLeft(10)
            .build();

    void assertContainsExactly(String... moves) {
        Collection<KuridorMove> legalMoves = testedState.getLegalPawnMoves();
        Collection<KuridorMove> expectedMoves = new HashSet<KuridorMove>();
        for (String move : moves) {
            expectedMoves.add(KuridorMove.pawn(move));
        }
        assertEquals(expectedMoves, legalMoves);
    }
}
