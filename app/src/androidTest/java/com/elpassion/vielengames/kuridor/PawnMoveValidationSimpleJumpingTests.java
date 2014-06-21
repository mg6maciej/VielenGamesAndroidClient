package com.elpassion.vielengames.kuridor;

import com.elpassion.vielengames.data.kuridor.KuridorGameState;

import java.util.Collections;

public final class PawnMoveValidationSimpleJumpingTests extends PawnMoveValidationBaseTestCase {

    public void testJumpingOnOpponentsHeadIsNotValid() {
        testedState = withOpponentToNorth;
        assertNotValid("e6");
    }

    public void testJumpingNorthOverOpponentIsValid() {
        testedState = withOpponentToNorth;
        assertValid("e7");
    }

    public void testJumpingSouthOverOpponentIsValid() {
        testedState = withOpponentToSouth;
        assertValid("e3");
    }

    public void testJumpingEastOverOpponentIsValid() {
        testedState = withOpponentToEast;
        assertValid("g5");
    }

    public void testJumpingWestOverOpponentIsValid() {
        testedState = withOpponentToWest;
        assertValid("c5");
    }

    private KuridorGameState withOpponentToNorth = KuridorGameState.builder()
            .team1(centered)
            .team2(northFromCenter)
            .walls(Collections.<String>emptyList())
            .activeTeam("team_1")
            .build();
    private KuridorGameState withOpponentToSouth = KuridorGameState.builder()
            .team1(centered)
            .team2(southFromCenter)
            .walls(Collections.<String>emptyList())
            .activeTeam("team_1")
            .build();
    private KuridorGameState withOpponentToEast = KuridorGameState.builder()
            .team1(centered)
            .team2(eastFromCenter)
            .walls(Collections.<String>emptyList())
            .activeTeam("team_1")
            .build();
    private KuridorGameState withOpponentToWest = KuridorGameState.builder()
            .team1(centered)
            .team2(westFromCenter)
            .walls(Collections.<String>emptyList())
            .activeTeam("team_1")
            .build();
}
