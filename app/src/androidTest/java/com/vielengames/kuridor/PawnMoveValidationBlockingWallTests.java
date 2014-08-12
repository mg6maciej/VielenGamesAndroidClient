package com.vielengames.kuridor;

import com.vielengames.data.Team;
import com.vielengames.data.kuridor.KuridorGameState;
import com.vielengames.data.kuridor.KuridorGameTeamState;

import java.util.HashMap;

import static com.vielengames.utils.Sets.set;

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

    private KuridorGameState blockedFromNorth = createGameState("e5h");
    private KuridorGameState withRandomWalls = createGameState("c3v");
    private KuridorGameState blockedFromNorthOtherSide = createGameState("d5h");
    private KuridorGameState blockedFromSouth = createGameState("e4h");
    private KuridorGameState blockedFromSouthOtherSide = createGameState("d4h");
    private KuridorGameState blockedFromEast = createGameState("e5v");
    private KuridorGameState blockedFromEastOtherSide = createGameState("e4v");
    private KuridorGameState blockedFromWest = createGameState("d5v");
    private KuridorGameState blockedFromWestOtherSide = createGameState("d4v");

    private KuridorGameState createGameState(String wall) {
        return KuridorGameState.builder()
                .teams(new HashMap<Team, KuridorGameTeamState>() {{
                    put(Team.FIRST, centered);
                    put(Team.SECOND, secondStarting);
                }})
                .walls(set(wall))
                .activeTeam(Team.FIRST)
                .build();
    }
}
