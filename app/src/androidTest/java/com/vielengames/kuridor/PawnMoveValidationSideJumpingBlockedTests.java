package com.vielengames.kuridor;

import com.vielengames.data.Team;
import com.vielengames.data.kuridor.KuridorGameState;
import com.vielengames.data.kuridor.KuridorGameTeamState;

import java.util.HashMap;

import static com.vielengames.utils.Sets.set;

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

    private KuridorGameState withOpponentToNorthAndWallsAllOverThem = createGameState(northFromCenter, "d6h", "d5v", "e6v");
    private KuridorGameState withOpponentToSouthAndWallsAllOverThem = createGameState(southFromCenter, "e3h", "d3v", "e4v");
    private KuridorGameState withOpponentToEastAndWallsAllOverThem = createGameState(eastFromCenter, "f5v", "e5h", "f4h");
    private KuridorGameState withOpponentToWestAndWallsAllOverThem = createGameState(westFromCenter, "c4v", "c5h", "d4h");

    private KuridorGameState createGameState(final KuridorGameTeamState secondTeamState, String... walls) {
        return KuridorGameState.builder()
                .teams(new HashMap<Team, KuridorGameTeamState>() {{
                    put(Team.FIRST, centered);
                    put(Team.SECOND, secondTeamState);
                }})
                .walls(set(walls))
                .activeTeam(Team.FIRST)
                .build();
    }
}
