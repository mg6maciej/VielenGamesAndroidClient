package com.vielengames.kuridor;

import com.vielengames.data.kuridor.KuridorGameState;
import com.vielengames.data.kuridor.KuridorGameTeamState;

import java.util.Arrays;
import java.util.HashMap;

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

    public void testWallsCannotPartiallyOverlap() {
        testedState = withSomeWalls;
        assertNotValid("a2v");
        assertNotValid("b3h");
        assertNotValid("d3h");
        assertNotValid("b4h");
        assertNotValid("d4h");
        assertNotValid("g5v");
        assertNotValid("g7v");
    }

    private KuridorGameState withSomeWalls = KuridorGameState.builder()
            .teams(new HashMap<String, KuridorGameTeamState>() {{
                put("team_1", KuridorGameTeamState.builder().pawnPosition("e1").wallsLeft(10).build());
                put("team_2", KuridorGameTeamState.builder().pawnPosition("e9").wallsLeft(10).build());
            }})
            .walls(Arrays.asList("a1v", "c3h", "c4h", "g6v"))
            .activeTeam("team_1")
            .build();
}
