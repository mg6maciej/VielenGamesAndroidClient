package com.vielengames.kuridor;

import com.vielengames.data.Team;
import com.vielengames.data.kuridor.KuridorGameState;
import com.vielengames.data.kuridor.KuridorGameTeamState;

import java.util.HashMap;

import static com.vielengames.utils.Sets.set;

public final class PawnMoveValidationWrongSideJumpingTests extends PawnMoveValidationBaseTestCase {

    public void testJumpingNorthAndEastOrWestIsInvalid() {
        testedState = withWallTwoStepsNorth;
        assertNotValid("d6");
        assertNotValid("f6");
    }

    public void testJumpingSouthAndEastOrWestIsInvalid() {
        testedState = withWallTwoStepsSouth;
        assertNotValid("d4");
        assertNotValid("f4");
    }

    public void testJumpingEastAndNorthOrSouthIsInvalid() {
        testedState = withWallTwoStepsEast;
        assertNotValid("f6");
        assertNotValid("f4");
    }

    public void testJumpingWestAndNorthOrSouthIsInvalid() {
        testedState = withWallTwoStepsWest;
        assertNotValid("d6");
        assertNotValid("d4");
    }

    private KuridorGameState withWallTwoStepsNorth = createGameState("e6h");
    private KuridorGameState withWallTwoStepsSouth = createGameState("d3h");
    private KuridorGameState withWallTwoStepsEast = createGameState("f4v");
    private KuridorGameState withWallTwoStepsWest = createGameState("c5v");

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
