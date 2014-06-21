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

    public void testCannotHidePawnInSnailHouse() {
        testedState = withSnailHouseFromWalls;
        assertValid("d3v");
        assertValid("d4v");
        assertValid("d5v");
        assertValid("d6v");
        assertNotValid("c3h");
        assertNotValid("d2v");
        assertNotValid("e2v");
        assertNotValid("f2v");
        assertNotValid("g3h");
        assertNotValid("g4h");
        assertNotValid("g5h");
        assertNotValid("g6h");
        assertNotValid("g7h");
        assertNotValid("f8v");
        assertNotValid("a7h");
        assertNotValid("a1h");
        assertNotValid("d7v");
        assertNotValid("d8v");
        assertNotValid("b8v");
        assertNotValid("d1v");
        assertNotValid("f1v");
        assertNotValid("h3h");
        assertNotValid("h5h");
        assertNotValid("h7h");
    }

    private KuridorGameState withAlmostBlockedPawn = KuridorGameState.builder()
            .team1(KuridorGameTeamState.builder().pawnPosition("e5").wallsLeft(10).build())
            .team2(KuridorGameTeamState.builder().pawnPosition("e9").wallsLeft(10).build())
            .walls(Arrays.asList("e5h", "d4v", "f4v"))
            .activeTeam("team_1")
            .build();

    private KuridorGameState withSnailHouseFromWalls = KuridorGameState.builder()
            .team1(KuridorGameTeamState.builder().pawnPosition("e5").wallsLeft(1).build())
            .team2(KuridorGameTeamState.builder().pawnPosition("e8").wallsLeft(0).build())
            .walls(Arrays.asList(
                    "e3h",
                    "f4v", "f6v",
                    "e7h", "c7h",
                    "b6v", "b4v", "b2v",
                    "c1h", "e1h", "g1h",
                    "h2v", "h4v", "h6v", "h8v",
                    "g8h", "e8h", "c8h", "a8h"))
            .activeTeam("team_1")
            .build();
}
