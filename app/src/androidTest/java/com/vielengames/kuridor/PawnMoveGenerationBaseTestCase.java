package com.vielengames.kuridor;

import com.vielengames.data.kuridor.KuridorGameState;
import com.vielengames.data.kuridor.KuridorGameTeamState;
import com.vielengames.data.kuridor.KuridorMove;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

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
    KuridorGameState withoutBlocks = KuridorGameState.builder()
            .teams(new HashMap<String, KuridorGameTeamState>() {{
                put("team_1", centered);
                put("team_2", secondStarting);
            }})
            .walls(Collections.<String>emptyList())
            .activeTeam("team_1")
            .build();

    void assertContainsAll(String... moves) {
        Collection<KuridorMove> legalMoves = testedState.getLegalPawnMoves();
        Collection<KuridorMove> expectedMoves = new HashSet<KuridorMove>();
        for (String move : moves) {
            expectedMoves.add(KuridorMove.pawn(move));
        }
        assertEquals(expectedMoves, legalMoves);
    }
}
