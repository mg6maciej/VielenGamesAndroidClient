package com.elpassion.vielengames.kuridor;

import com.elpassion.vielengames.data.kuridor.KuridorGameState;

import java.util.Arrays;

public final class PawnMoveValidationBlockingWallTests extends PawnMoveValidationBaseTestCase {

    public void testJumpingOverNorthWallIsNotValid() {
        testedState = blockedFromNorth;
        assertNotValid("e6");
    }

    public void testWhenWallsDoNotBlockMeIShallPass() {
        testedState = withRandomWalls;
        assertValid("e6");
    }

    public void testJumpingOverOtherSideOfNorthWallIsNotValid() {
        testedState = blockedFromNorthOtherSide;
        assertNotValid("e6");
    }

    public void testJumpingOverSouthWallIsNotValid() {
        testedState = blockedFromSouth;
        assertNotValid("e4");
    }

    public void testJumpingOverOtherSideOfSouthWallIsNotValid() {
        testedState = blockedFromSouthOtherSide;
        assertNotValid("e4");
    }

    public void testJumpingOverEastWallIsNotValid() {
        testedState = blockedFromEast;
        assertNotValid("f5");
    }

    public void testJumpingOverOtherSideOfEastWallIsNotValid() {
        testedState = blockedFromEastOtherSide;
        assertNotValid("f5");
    }

    public void testJumpingOverWestWallIsNotValid() {
        testedState = blockedFromWest;
        assertNotValid("d5");
    }

    public void testJumpingOverOtherSideOfWestWallIsNotValid() {
        testedState = blockedFromWestOtherSide;
        assertNotValid("d5");
    }

    private KuridorGameState blockedFromNorth = KuridorGameState.builder()
            .team1(centered)
            .team2(secondStarting)
            .walls(Arrays.asList("e5h"))
            .activeTeam("team_1")
            .build();
    private KuridorGameState withRandomWalls = KuridorGameState.builder()
            .team1(centered)
            .team2(secondStarting)
            .walls(Arrays.asList("c3v"))
            .activeTeam("team_1")
            .build();
    private KuridorGameState blockedFromNorthOtherSide = KuridorGameState.builder()
            .team1(centered)
            .team2(secondStarting)
            .walls(Arrays.asList("d5h"))
            .activeTeam("team_1")
            .build();
    private KuridorGameState blockedFromSouth = KuridorGameState.builder()
            .team1(centered)
            .team2(secondStarting)
            .walls(Arrays.asList("e4h"))
            .activeTeam("team_1")
            .build();
    private KuridorGameState blockedFromSouthOtherSide = KuridorGameState.builder()
            .team1(centered)
            .team2(secondStarting)
            .walls(Arrays.asList("d4h"))
            .activeTeam("team_1")
            .build();
    private KuridorGameState blockedFromEast = KuridorGameState.builder()
            .team1(centered)
            .team2(secondStarting)
            .walls(Arrays.asList("e5v"))
            .activeTeam("team_1")
            .build();
    private KuridorGameState blockedFromEastOtherSide = KuridorGameState.builder()
            .team1(centered)
            .team2(secondStarting)
            .walls(Arrays.asList("e4v"))
            .activeTeam("team_1")
            .build();
    private KuridorGameState blockedFromWest = KuridorGameState.builder()
            .team1(centered)
            .team2(secondStarting)
            .walls(Arrays.asList("d5v"))
            .activeTeam("team_1")
            .build();
    private KuridorGameState blockedFromWestOtherSide = KuridorGameState.builder()
            .team1(centered)
            .team2(secondStarting)
            .walls(Arrays.asList("d4v"))
            .activeTeam("team_1")
            .build();
}
