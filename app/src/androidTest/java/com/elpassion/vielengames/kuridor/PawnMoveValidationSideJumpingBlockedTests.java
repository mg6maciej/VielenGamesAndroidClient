package com.elpassion.vielengames.kuridor;

import com.elpassion.vielengames.data.kuridor.KuridorGameState;

import java.util.Arrays;

public final class PawnMoveValidationSideJumpingBlockedTests extends PawnMoveValidationBaseTestCase {

    public void testJumpingNorthOnOpponentsHeadAndToEastOrWestIsValid() {
        testedState = withOpponentToNorthAndWallsAllOverThem;
        assertNotValid("d6");
        assertNotValid("f6");
    }

    public void testJumpingSouthOnOpponentsHeadAndToEastOrWestIsValid() {
        testedState = withOpponentToSouthAndWallsAllOverThem;
        assertNotValid("d4");
        assertNotValid("f4");
    }

    public void testJumpingEastOnOpponentsHeadAndToNorthOrSouthIsValid() {
        testedState = withOpponentToEastAndWallsAllOverThem;
        assertNotValid("f6");
        assertNotValid("f4");
    }

    public void testJumpingWestOnOpponentsHeadAndToNorthOrSouthIsValid() {
        testedState = withOpponentToWestAndWallsAllOverThem;
        assertNotValid("d6");
        assertNotValid("d4");
    }

    private KuridorGameState withOpponentToNorthAndWallsAllOverThem = KuridorGameState.builder()
            .team1(centered)
            .team2(northFromCenter)
            .walls(Arrays.asList("d6h", "d5v", "e6v"))
            .activeTeam("team_1")
            .build();
    private KuridorGameState withOpponentToSouthAndWallsAllOverThem = KuridorGameState.builder()
            .team1(centered)
            .team2(southFromCenter)
            .walls(Arrays.asList("e3h", "d3v", "e4v"))
            .activeTeam("team_1")
            .build();
    private KuridorGameState withOpponentToEastAndWallsAllOverThem = KuridorGameState.builder()
            .team1(centered)
            .team2(eastFromCenter)
            .walls(Arrays.asList("f5v", "e5h", "f4h"))
            .activeTeam("team_1")
            .build();
    private KuridorGameState withOpponentToWestAndWallsAllOverThem = KuridorGameState.builder()
            .team1(centered)
            .team2(westFromCenter)
            .walls(Arrays.asList("c4v", "c5h", "d4h"))
            .activeTeam("team_1")
            .build();
}
