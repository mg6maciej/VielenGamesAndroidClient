package com.vielengames.kuridor;

import com.vielengames.data.kuridor.KuridorGameState;
import com.vielengames.data.kuridor.KuridorGameTeamState;

import java.util.Collections;
import java.util.HashMap;

public final class PawnMoveGenerationCannotJumpOnOppTests extends PawnMoveGenerationBaseTestCase {

    public void testCannotJumpOnOppToNorth() {
        testedState = closeToEachOther;
        assertContainsExactly("f5", "e4", "d5", "e7");
    }

    public void testCannotJumpOutsideOfBoard() {
        testedState = closeToEachOtherAndEndOfBoard;
        assertContainsExactly("d8", "e7", "f8", "f9", "d9");
    }

    KuridorGameState closeToEachOther = KuridorGameState.builder()
            .teams(new HashMap<String, KuridorGameTeamState>() {{
                put("team_1", centered);
                put("team_2", northFromCenter);
            }})
            .walls(Collections.<String>emptyList())
            .activeTeam("team_1")
            .build();

    KuridorGameState closeToEachOtherAndEndOfBoard = KuridorGameState.builder()
            .teams(new HashMap<String, KuridorGameTeamState>() {{
                put("team_1", belowSecondStarting);
                put("team_2", secondStarting);
            }})
            .walls(Collections.<String>emptyList())
            .activeTeam("team_1")
            .build();
}
