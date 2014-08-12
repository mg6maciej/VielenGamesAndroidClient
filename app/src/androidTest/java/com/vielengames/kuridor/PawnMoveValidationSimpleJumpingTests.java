package com.vielengames.kuridor;

import com.vielengames.data.Team;
import com.vielengames.data.kuridor.KuridorGameState;
import com.vielengames.data.kuridor.KuridorGameTeamState;

import java.util.Collections;
import java.util.HashMap;

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

    private KuridorGameState withOpponentToNorth = createGameState(northFromCenter);
    private KuridorGameState withOpponentToSouth = createGameState(southFromCenter);
    private KuridorGameState withOpponentToEast = createGameState(eastFromCenter);
    private KuridorGameState withOpponentToWest = createGameState(westFromCenter);

    private KuridorGameState createGameState(final KuridorGameTeamState secondTeamState) {
        return KuridorGameState.builder()
                .teams(new HashMap<Team, KuridorGameTeamState>() {{
                    put(Team.FIRST, centered);
                    put(Team.SECOND, secondTeamState);
                }})
                .walls(Collections.<String>emptySet())
                .activeTeam(Team.FIRST)
                .build();
    }
}
