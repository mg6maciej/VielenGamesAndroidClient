package com.elpassion.vielengames.kuridor;

import com.elpassion.vielengames.data.kuridor.KuridorGameState;
import com.elpassion.vielengames.data.kuridor.KuridorGameTeamState;
import com.elpassion.vielengames.data.kuridor.KuridorMove;

import junit.framework.TestCase;

import java.util.Arrays;
import java.util.Collections;

public final class PawnMoveValidationTests extends TestCase {

    private KuridorGameState testedState;

    public void testWeirdMovesAreNotValid() {
        testedState = withoutBlocks;
        assertNotValid("");
        assertNotValid("f");
        assertNotValid("8");
        assertNotValid("test");
        assertNotValid("1a");
        assertNotValid("j5");
        assertNotValid("gg");
        assertNotValid("c10");
        assertNotValid("d0");
        assertNotValid("66");
        assertNotValid("d4h");
        assertNotValid("d4v");
    }

    public void testSimpleMovesAreValid() {
        testedState = withoutBlocks;
        assertValid("e4");
        assertValid("d5");
        assertValid("f5");
        assertValid("e6");
    }

    public void testFarMovesAreNotValid() {
        testedState = withoutBlocks;
        assertNotValid("a1");
        assertNotValid("a9");
        assertNotValid("i1");
        assertNotValid("i9");
    }

    public void testNotSoNearMovesAreNotValid() {
        testedState = withoutBlocks;
        assertNotValid("e7");
        assertNotValid("f6");
        assertNotValid("g5");
        assertNotValid("f4");
        assertNotValid("e3");
        assertNotValid("d4");
        assertNotValid("c5");
        assertNotValid("d6");
    }

    public void testZeroLengthMoveIsNotValid() {
        testedState = withoutBlocks;
        assertNotValid("e5");
    }

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

    private KuridorGameTeamState centered = KuridorGameTeamState.builder()
            .pawnPosition("e5")
            .wallsLeft(10)
            .build();
    private KuridorGameTeamState secondStarting = KuridorGameTeamState.builder()
            .pawnPosition("e9")
            .wallsLeft(10)
            .build();
    private KuridorGameTeamState northFromCenter = KuridorGameTeamState.builder()
            .pawnPosition("e6")
            .wallsLeft(10)
            .build();
    private KuridorGameTeamState southFromCenter = KuridorGameTeamState.builder()
            .pawnPosition("e4")
            .wallsLeft(10)
            .build();
    private KuridorGameTeamState eastFromCenter = KuridorGameTeamState.builder()
            .pawnPosition("f5")
            .wallsLeft(10)
            .build();
    private KuridorGameTeamState westFromCenter = KuridorGameTeamState.builder()
            .pawnPosition("d5")
            .wallsLeft(10)
            .build();

    private KuridorGameState withoutBlocks = KuridorGameState.builder()
            .team1(centered)
            .team2(secondStarting)
            .walls(Collections.<String>emptyList())
            .activeTeam("team_1")
            .build();
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

    private boolean valid(String position) {
        return testedState.isMoveValid(KuridorMove.pawn(position));
    }

    private void assertValid(String position) {
        assertTrue(valid(position));
    }

    private void assertNotValid(String position) {
        assertFalse(valid(position));
    }
}
