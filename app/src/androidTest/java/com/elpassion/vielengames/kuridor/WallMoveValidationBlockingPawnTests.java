package com.elpassion.vielengames.kuridor;

import com.elpassion.vielengames.data.kuridor.KuridorGameState;
import com.elpassion.vielengames.data.kuridor.KuridorGameTeamState;

import java.util.Arrays;

public final class WallMoveValidationBlockingPawnTests extends WallMoveValidationBaseTestCase {

    public void testCannotCompletelyBoxPawn() {
        testedState = withAlmostBlockedPawn;
        assertNotValid("e4h");
        assertNotValid("e3h");
        assertValid("e2h");
    }

    private KuridorGameState withAlmostBlockedPawn = KuridorGameState.builder()
            .team1(KuridorGameTeamState.builder().pawnPosition("e5").wallsLeft(10).build())
            .team2(KuridorGameTeamState.builder().pawnPosition("e9").wallsLeft(10).build())
            .walls(Arrays.asList("e5h", "d4v", "f4v"))
            .activeTeam("team_1")
            .build();
}
