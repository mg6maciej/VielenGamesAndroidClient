package com.elpassion.vielengames.kuridor;

import com.elpassion.vielengames.data.kuridor.KuridorGameState;
import com.elpassion.vielengames.data.kuridor.KuridorGameTeamState;

import java.util.Arrays;

public final class WallMoveValidationOverlappingTests extends WallMoveValidationBaseTestCase {

    public void testWallsCannotFullyOverlap() {
        testedState = withSomeWalls;
        assertNotValid("a1v");
        assertNotValid("c3h");
        assertNotValid("c4h");
        assertNotValid("g6v");
    }

    public void testWallsCannotCross() {
        testedState = withSomeWalls;
        assertNotValid("a1h");
        assertNotValid("c3v");
        assertNotValid("c4v");
        assertNotValid("g6h");
    }

    private KuridorGameState withSomeWalls = KuridorGameState.builder()
            .team1(KuridorGameTeamState.builder().pawnPosition("e1").wallsLeft(10).build())
            .team2(KuridorGameTeamState.builder().pawnPosition("e9").wallsLeft(10).build())
            .walls(Arrays.asList("a1v", "c3h", "c4h", "g6v"))
            .activeTeam("team_1")
            .build();
}
