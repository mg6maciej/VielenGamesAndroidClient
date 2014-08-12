package com.vielengames.kuridor;

import com.vielengames.data.Team;
import com.vielengames.data.kuridor.KuridorGameState;
import com.vielengames.data.kuridor.KuridorGameTeamState;

import java.util.HashMap;

import static com.vielengames.utils.Sets.set;

public final class PawnMoveGenerationCanJumpLeftOrRightWhenWallBehindOpponentTests extends PawnMoveGenerationBaseTestCase {

    public void testCanJumpLeftOrRightAfterHittingWallBehindOpp() {
        testedState = closeToEachOtherAndWallBehind;
        assertContainsExactly("f5", "e4", "d5", "d6", "f6");
    }

    KuridorGameState closeToEachOtherAndWallBehind = KuridorGameState.builder()
            .teams(new HashMap<Team, KuridorGameTeamState>() {{
                put(Team.FIRST, centered);
                put(Team.SECOND, northFromCenter);
            }})
            .walls(set("d6h"))
            .activeTeam(Team.FIRST)
            .build();
}
