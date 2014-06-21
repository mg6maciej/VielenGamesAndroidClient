package com.elpassion.vielengames.kuridor;

import com.elpassion.vielengames.data.kuridor.KuridorGameState;

import java.util.Arrays;
import java.util.Collections;

public final class PawnMoveValidationSideJumpingTests extends PawnMoveValidationBaseTestCase {

    public void testJumpingNorthOnOpponentsHeadAndToEastOrWestIsValid() {
        testedState = withOpponentToNorthAndWallBehindThem;
        assertValid("d6");
        assertValid("f6");
    }

    public void testJumpingSouthOnOpponentsHeadAndToEastOrWestIsValid() {
        testedState = withOpponentToSouthAndWallBehindThem;
        assertValid("d4");
        assertValid("f4");
    }

    public void testJumpingEastOnOpponentsHeadAndToNorthOrSouthIsValid() {
        testedState = withOpponentToEastAndWallBehindThem;
        assertValid("f6");
        assertValid("f4");
    }

    public void testJumpingWestOnOpponentsHeadAndToNorthOrSouthIsValid() {
        testedState = withOpponentToWestAndWallBehindThem;
        assertValid("d6");
        assertValid("d4");
    }

    private KuridorGameState withOpponentToNorthAndWallBehindThem = KuridorGameState.builder()
            .team1(centered)
            .team2(northFromCenter)
            .walls(Arrays.asList("e6h"))
            .activeTeam("team_1")
            .build();
    private KuridorGameState withOpponentToSouthAndWallBehindThem = KuridorGameState.builder()
            .team1(centered)
            .team2(southFromCenter)
            .walls(Arrays.asList("d3h"))
            .activeTeam("team_1")
            .build();
    private KuridorGameState withOpponentToEastAndWallBehindThem = KuridorGameState.builder()
            .team1(centered)
            .team2(eastFromCenter)
            .walls(Arrays.asList("f4v"))
            .activeTeam("team_1")
            .build();
    private KuridorGameState withOpponentToWestAndWallBehindThem = KuridorGameState.builder()
            .team1(centered)
            .team2(westFromCenter)
            .walls(Arrays.asList("c5v"))
            .activeTeam("team_1")
            .build();
}
