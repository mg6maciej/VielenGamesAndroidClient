package com.vielengames.kuridor;

import com.vielengames.data.kuridor.KuridorGameState;
import com.vielengames.data.kuridor.KuridorGameTeamState;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

public final class PawnMoveGenerationCannotJumpLeftOrRightWhenBlockedTests extends PawnMoveGenerationBaseTestCase {

    public void testCannotJumpLeftOrRightWhenWallAround() {
        testedState = closeToEachOtherAndWallsAround;
        assertContainsExactly("e4");
    }

    public void testCannotJumpLeftWhenNearEndOfBoard() {
        testedState = closeToEachOtherAndEndOfBoardOnLeft;
        assertContainsExactly("a4", "b5", "b6");
    }

    KuridorGameState closeToEachOtherAndWallsAround = KuridorGameState.builder()
            .teams(new HashMap<String, KuridorGameTeamState>() {{
                put("team_1", centered);
                put("team_2", northFromCenter);
            }})
            .walls(Arrays.asList("e6h", "e5v", "d5v"))
            .activeTeam("team_1")
            .build();

    KuridorGameState closeToEachOtherAndEndOfBoardOnLeft = KuridorGameState.builder()
            .teams(new HashMap<String, KuridorGameTeamState>() {{
                put("team_1", a5);
                put("team_2", a6);
            }})
            .walls(Collections.singletonList("a6h"))
            .activeTeam("team_1")
            .build();
}
