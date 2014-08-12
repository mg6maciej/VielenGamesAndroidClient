package com.vielengames.kuridor;

import com.vielengames.data.Team;
import com.vielengames.data.kuridor.KuridorGameState;
import com.vielengames.data.kuridor.KuridorGameTeamState;
import com.vielengames.utils.Sets;

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
            .teams(new HashMap<Team, KuridorGameTeamState>() {{
                put(Team.FIRST, centered);
                put(Team.SECOND, secondStarting);
            }})
            .walls(Sets.<String>set())
            .activeTeam(Team.FIRST)
            .build();
}
