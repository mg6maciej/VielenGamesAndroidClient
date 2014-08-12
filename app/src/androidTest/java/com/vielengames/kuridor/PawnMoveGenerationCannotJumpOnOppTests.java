package com.vielengames.kuridor;

import com.vielengames.data.Team;
import com.vielengames.data.kuridor.KuridorGameState;
import com.vielengames.data.kuridor.KuridorGameTeamState;
import com.vielengames.utils.Sets;

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
            .teams(new HashMap<Team, KuridorGameTeamState>() {{
                put(Team.FIRST, centered);
                put(Team.SECOND, northFromCenter);
            }})
            .walls(Sets.<String>set())
            .activeTeam(Team.FIRST)
            .build();

    KuridorGameState closeToEachOtherAndEndOfBoard = KuridorGameState.builder()
            .teams(new HashMap<Team, KuridorGameTeamState>() {{
                put(Team.FIRST, belowSecondStarting);
                put(Team.SECOND, secondStarting);
            }})
            .walls(Sets.<String>set())
            .activeTeam(Team.FIRST)
            .build();
}
