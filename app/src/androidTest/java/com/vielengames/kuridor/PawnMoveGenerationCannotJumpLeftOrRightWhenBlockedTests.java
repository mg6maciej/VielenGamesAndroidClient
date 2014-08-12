package com.vielengames.kuridor;

import com.vielengames.data.Team;
import com.vielengames.data.kuridor.KuridorGameState;
import com.vielengames.data.kuridor.KuridorGameTeamState;

import java.util.HashMap;

import static com.vielengames.utils.Sets.set;

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
            .teams(new HashMap<Team, KuridorGameTeamState>() {{
                put(Team.FIRST, centered);
                put(Team.SECOND, northFromCenter);
            }})
            .walls(set("e6h", "e5v", "d5v"))
            .activeTeam(Team.FIRST)
            .build();

    KuridorGameState closeToEachOtherAndEndOfBoardOnLeft = KuridorGameState.builder()
            .teams(new HashMap<Team, KuridorGameTeamState>() {{
                put(Team.FIRST, a5);
                put(Team.SECOND, a6);
            }})
            .walls(set("a6h"))
            .activeTeam(Team.FIRST)
            .build();
}
