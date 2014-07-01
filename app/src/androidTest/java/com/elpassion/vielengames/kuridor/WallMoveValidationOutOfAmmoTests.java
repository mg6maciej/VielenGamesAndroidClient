package com.elpassion.vielengames.kuridor;

import com.elpassion.vielengames.data.kuridor.KuridorGameState;
import com.elpassion.vielengames.data.kuridor.KuridorGameTeamState;

import java.util.Arrays;
import java.util.HashMap;

public final class WallMoveValidationOutOfAmmoTests extends WallMoveValidationBaseTestCase {

    public void testCannotPutWallIfNoMoreLeft() {
        testedState = withNoMoreWallsLeft;
        assertNotValid("e5h");
        assertNotValid("e5v");
    }

    private KuridorGameState withNoMoreWallsLeft = KuridorGameState.builder()
            .teams(new HashMap<String, KuridorGameTeamState>() {{
                put("team_1", KuridorGameTeamState.builder().pawnPosition("e1").wallsLeft(0).build());
                put("team_2", KuridorGameTeamState.builder().pawnPosition("e9").wallsLeft(10).build());
            }})
            .activeTeam("team_1")
            .build();
}
