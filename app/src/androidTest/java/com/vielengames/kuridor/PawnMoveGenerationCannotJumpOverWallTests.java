package com.vielengames.kuridor;

import com.vielengames.data.Team;
import com.vielengames.data.kuridor.KuridorGameState;
import com.vielengames.data.kuridor.KuridorGameTeamState;

import java.util.HashMap;

import static com.vielengames.utils.Sets.set;

public final class PawnMoveGenerationCannotJumpOverWallTests extends PawnMoveGenerationBaseTestCase {

    public void testCannotJumpOverWallToNorth() {
        testedState = withWallToNorth;
        assertContainsExactly("d5", "e4", "f5");
    }

    KuridorGameState withWallToNorth = KuridorGameState.builder()
            .teams(new HashMap<Team, KuridorGameTeamState>() {{
                put(Team.FIRST, centered);
                put(Team.SECOND, secondStarting);
            }})
            .walls(set("e5h"))
            .activeTeam(Team.FIRST)
            .build();
}
