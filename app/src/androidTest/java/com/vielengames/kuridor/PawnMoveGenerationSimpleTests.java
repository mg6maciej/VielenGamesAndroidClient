package com.vielengames.kuridor;

import com.vielengames.data.kuridor.KuridorGameState;
import com.vielengames.data.kuridor.KuridorGameTeamState;

import java.util.Collections;
import java.util.HashMap;

public final class PawnMoveGenerationSimpleTests extends PawnMoveGenerationBaseTestCase {

    public void testAllSimpleMovesValid() {
        testedState = withoutBlocks;
        assertContainsExactly("e6", "f5", "e4", "d5");
    }

    public void testAllSimpleMovesWhenNextToEndOfBoardValid() {
        testedState = KuridorGameState.initial();
        assertContainsExactly("e2", "d1", "f1");
    }

    KuridorGameState withoutBlocks = KuridorGameState.builder()
            .teams(new HashMap<String, KuridorGameTeamState>() {{
                put("team_1", centered);
                put("team_2", secondStarting);
            }})
            .walls(Collections.<String>emptyList())
            .activeTeam("team_1")
            .build();
}
