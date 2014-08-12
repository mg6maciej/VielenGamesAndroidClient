package com.vielengames.kuridor;

import com.vielengames.data.Team;
import com.vielengames.data.kuridor.KuridorGameState;
import com.vielengames.data.kuridor.KuridorGameTeamState;

import java.util.HashMap;

import static com.vielengames.utils.Sets.set;

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
            .teams(new HashMap<Team, KuridorGameTeamState>() {{
                put(Team.FIRST, KuridorGameTeamState.builder().pawnPosition("e1").wallsLeft(10).build());
                put(Team.SECOND, KuridorGameTeamState.builder().pawnPosition("e9").wallsLeft(10).build());
            }})
            .walls(set("a1v", "c3h", "c4h", "g6v"))
            .activeTeam(Team.FIRST)
            .build();
}
