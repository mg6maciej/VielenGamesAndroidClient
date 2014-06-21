package com.elpassion.vielengames.kuridor;

import com.elpassion.vielengames.data.kuridor.KuridorGameState;

import java.util.Arrays;

public final class PawnMoveValidationBlockedJumpingTests extends PawnMoveValidationBaseTestCase {

    public void testJumpingNorthOverWallToJoinOpponentIsNotValid() {
        testedState = withOpponentToNorthAndWallBetweenUs;
        assertNotValid("e7");
    }

    public void testJumpingSouthOverWallToJoinOpponentIsNotValid() {
        testedState = withOpponentToSouthAndWallBetweenUs;
        assertNotValid("e3");
    }

    public void testJumpingEastOverWallToJoinOpponentIsNotValid() {
        testedState = withOpponentToEastAndWallBetweenUs;
        assertNotValid("g5");
    }

    public void testJumpingWestOverWallToJoinOpponentIsNotValid() {
        testedState = withOpponentToWestAndWallBetweenUs;
        assertNotValid("c5");
    }

    public void testJumpingNorthOverOpponentAndWallIsNotValid() {
        testedState = withOpponentToNorthAndWallBehindThem;
        assertNotValid("e7");
    }

    public void testJumpingSouthOverOpponentAndWallIsNotValid() {
        testedState = withOpponentToSouthAndWallBehindThem;
        assertNotValid("e3");
    }

    public void testJumpingEastOverOpponentAndWallIsNotValid() {
        testedState = withOpponentToEastAndWallBehindThem;
        assertNotValid("g5");
    }

    public void testJumpingWestOverOpponentAndWallIsNotValid() {
        testedState = withOpponentToWestAndWallBehindThem;
        assertNotValid("c5");
    }

    private KuridorGameState withOpponentToNorthAndWallBetweenUs = KuridorGameState.builder()
            .team1(centered)
            .team2(northFromCenter)
            .walls(Arrays.asList("e5h"))
            .activeTeam("team_1")
            .build();
    private KuridorGameState withOpponentToSouthAndWallBetweenUs = KuridorGameState.builder()
            .team1(centered)
            .team2(southFromCenter)
            .walls(Arrays.asList("d4h"))
            .activeTeam("team_1")
            .build();
    private KuridorGameState withOpponentToEastAndWallBetweenUs = KuridorGameState.builder()
            .team1(centered)
            .team2(eastFromCenter)
            .walls(Arrays.asList("e4v"))
            .activeTeam("team_1")
            .build();
    private KuridorGameState withOpponentToWestAndWallBetweenUs = KuridorGameState.builder()
            .team1(centered)
            .team2(westFromCenter)
            .walls(Arrays.asList("d5v"))
            .activeTeam("team_1")
            .build();
    private KuridorGameState withOpponentToNorthAndWallBehindThem = KuridorGameState.builder()
            .team1(centered)
            .team2(northFromCenter)
            .walls(Arrays.asList("d6h"))
            .activeTeam("team_1")
            .build();
    private KuridorGameState withOpponentToSouthAndWallBehindThem = KuridorGameState.builder()
            .team1(centered)
            .team2(southFromCenter)
            .walls(Arrays.asList("e3h"))
            .activeTeam("team_1")
            .build();
    private KuridorGameState withOpponentToEastAndWallBehindThem = KuridorGameState.builder()
            .team1(centered)
            .team2(eastFromCenter)
            .walls(Arrays.asList("f5v"))
            .activeTeam("team_1")
            .build();
    private KuridorGameState withOpponentToWestAndWallBehindThem = KuridorGameState.builder()
            .team1(centered)
            .team2(westFromCenter)
            .walls(Arrays.asList("c4v"))
            .activeTeam("team_1")
            .build();
}
