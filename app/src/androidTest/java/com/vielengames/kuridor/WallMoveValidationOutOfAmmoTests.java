package com.vielengames.kuridor;

import com.vielengames.data.Team;
import com.vielengames.data.kuridor.KuridorGameState;
import com.vielengames.data.kuridor.KuridorGameTeamState;

import java.util.HashMap;

public final class WallMoveValidationOutOfAmmoTests extends WallMoveValidationBaseTestCase {

    public void testCannotPutWallIfNoMoreLeft() {
        testedState = withNoMoreWallsLeft;
        assertNotValid("e5h");
        assertNotValid("e5v");
    }

    private KuridorGameState withNoMoreWallsLeft = KuridorGameState.builder()
            .teams(new HashMap<Team, KuridorGameTeamState>() {{
                put(Team.FIRST, KuridorGameTeamState.builder().pawnPosition("e1").wallsLeft(0).build());
                put(Team.SECOND, KuridorGameTeamState.builder().pawnPosition("e9").wallsLeft(10).build());
            }})
            .activeTeam(Team.FIRST)
            .build();
}
