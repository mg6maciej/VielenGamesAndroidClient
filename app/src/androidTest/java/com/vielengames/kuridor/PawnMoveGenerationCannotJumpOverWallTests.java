package com.vielengames.kuridor;

import com.vielengames.data.kuridor.KuridorGameState;
import com.vielengames.data.kuridor.KuridorGameTeamState;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

public final class PawnMoveGenerationCannotJumpOverWallTests extends PawnMoveGenerationBaseTestCase {

    public void testCannotJumpOverWallToNorth() {
        testedState = withWallToNorth;
        assertContainsExactly("d5", "e4", "f5");
    }

    KuridorGameState withWallToNorth = KuridorGameState.builder()
            .teams(new HashMap<String, KuridorGameTeamState>() {{
                put("team_1", centered);
                put("team_2", secondStarting);
            }})
            .walls(Collections.singletonList("e5h"))
            .activeTeam("team_1")
            .build();
}
