package com.vielengames.kuridor;

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
            .teams(new HashMap<String, KuridorGameTeamState>() {{
                put("team_1", centered);
                put("team_2", northFromCenter);
            }})
            .walls(set("d6h"))
            .activeTeam("team_1")
            .build();
}
