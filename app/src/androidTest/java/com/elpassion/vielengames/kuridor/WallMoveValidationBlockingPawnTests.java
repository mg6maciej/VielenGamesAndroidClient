package com.elpassion.vielengames.kuridor;

import com.elpassion.vielengames.data.kuridor.KuridorGameState;
import com.elpassion.vielengames.data.kuridor.KuridorGameTeamState;

import java.util.Arrays;
import java.util.HashMap;

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

    public void testCanSeparatePlayersOnGoodSides() {
        testedState = withPlayersAlmostSeparatedOnGoodSides;
        assertValid("h6h");
        assertValid("h4h");
    }

    public void testCannotSeparatePlayersOnWrongSides() {
        testedState = withPlayersAlmostSeparatedOnWrongSides;
        assertNotValid("h6h");
        assertNotValid("h4h");
    }

    public void testCannotBlockPlayersOnNorthSide() {
        testedState = withPlayersAlmostSeparatedOnNorthSide;
        assertNotValid("h6h");
        assertNotValid("h4h");
    }

    public void testCannotBlockPlayersOnSouthSide() {
        testedState = withPlayersAlmostSeparatedOnSouthSide;
        assertNotValid("h6h");
        assertNotValid("h4h");
    }

    public void testCannotCompletelyBoxPawnToNorthOrSouthSide() {
        testedState = withAlmostBlockedPawnsOnNorthAndSouthSides;
        assertNotValid("d2h");
        assertNotValid("e2h");
        assertNotValid("d7h");
        assertNotValid("e7h");
    }

    public void testCannotCompletelyBoxPawnToEastOrWestSide() {
        testedState = withAlmostBlockedPawnsOnEastAndWestSides;
        assertNotValid("b4v");
        assertNotValid("b5v");
        assertNotValid("g4v");
        assertNotValid("g5v");
    }

    private KuridorGameState withAlmostBlockedPawn = KuridorGameState.builder()
            .teams(new HashMap<String, KuridorGameTeamState>() {{
                put("team_1", KuridorGameTeamState.builder().pawnPosition("e5").wallsLeft(10).build());
                put("team_2", KuridorGameTeamState.builder().pawnPosition("e9").wallsLeft(10).build());
            }})
            .walls(Arrays.asList("e5h", "d4v", "f4v"))
            .activeTeam("team_1")
            .build();

    private KuridorGameState withSnailHouseFromWalls = KuridorGameState.builder()
            .teams(new HashMap<String, KuridorGameTeamState>() {{
                put("team_1", KuridorGameTeamState.builder().pawnPosition("e5").wallsLeft(1).build());
                put("team_2", KuridorGameTeamState.builder().pawnPosition("e8").wallsLeft(0).build());
            }})
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

    private KuridorGameState withPlayersAlmostSeparatedOnGoodSides = KuridorGameState.builder()
            .teams(new HashMap<String, KuridorGameTeamState>() {{
                put("team_1", KuridorGameTeamState.builder().pawnPosition("e6").wallsLeft(5).build());
                put("team_2", KuridorGameTeamState.builder().pawnPosition("e5").wallsLeft(5).build());
            }})
            .walls(Arrays.asList("a5h", "c5h", "e5h", "g5h", "h5v"))
            .activeTeam("team_1")
            .build();

    private KuridorGameState withPlayersAlmostSeparatedOnWrongSides = KuridorGameState.builder()
            .teams(new HashMap<String, KuridorGameTeamState>() {{
                put("team_1", KuridorGameTeamState.builder().pawnPosition("e5").wallsLeft(5).build());
                put("team_2", KuridorGameTeamState.builder().pawnPosition("e6").wallsLeft(5).build());
            }})
            .walls(Arrays.asList("a5h", "c5h", "e5h", "g5h", "h5v"))
            .activeTeam("team_1")
            .build();

    private KuridorGameState withPlayersAlmostSeparatedOnNorthSide = KuridorGameState.builder()
            .teams(new HashMap<String, KuridorGameTeamState>() {{
                put("team_1", KuridorGameTeamState.builder().pawnPosition("f6").wallsLeft(5).build());
                put("team_2", KuridorGameTeamState.builder().pawnPosition("e6").wallsLeft(5).build());
            }})
            .walls(Arrays.asList("a5h", "c5h", "e5h", "g5h", "h5v"))
            .activeTeam("team_1")
            .build();

    private KuridorGameState withPlayersAlmostSeparatedOnSouthSide = KuridorGameState.builder()
            .teams(new HashMap<String, KuridorGameTeamState>() {{
                put("team_1", KuridorGameTeamState.builder().pawnPosition("e5").wallsLeft(5).build());
                put("team_2", KuridorGameTeamState.builder().pawnPosition("f5").wallsLeft(5).build());
            }})
            .walls(Arrays.asList("a5h", "c5h", "e5h", "g5h", "h5v"))
            .activeTeam("team_1")
            .build();

    private KuridorGameState withAlmostBlockedPawnsOnNorthAndSouthSides = KuridorGameState.builder()
            .teams(new HashMap<String, KuridorGameTeamState>() {{
                put("team_1", KuridorGameTeamState.builder().pawnPosition("e1").wallsLeft(8).build());
                put("team_2", KuridorGameTeamState.builder().pawnPosition("e9").wallsLeft(8).build());
            }})
            .walls(Arrays.asList("d1v", "e1v", "d8v", "e8v"))
            .activeTeam("team_1")
            .build();

    private KuridorGameState withAlmostBlockedPawnsOnEastAndWestSides = KuridorGameState.builder()
            .teams(new HashMap<String, KuridorGameTeamState>() {{
                put("team_1", KuridorGameTeamState.builder().pawnPosition("a5").wallsLeft(8).build());
                put("team_2", KuridorGameTeamState.builder().pawnPosition("i5").wallsLeft(8).build());
            }})
            .walls(Arrays.asList("a4h", "a5h", "h4h", "h5h"))
            .activeTeam("team_1")
            .build();
}
