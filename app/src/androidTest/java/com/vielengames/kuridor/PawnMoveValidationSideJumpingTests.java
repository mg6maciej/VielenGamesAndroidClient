package com.vielengames.kuridor;

import com.vielengames.data.kuridor.KuridorGameState;
import com.vielengames.data.kuridor.KuridorGameTeamState;

import java.util.HashMap;

import static com.vielengames.utils.Sets.set;

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

    private KuridorGameState withOpponentToNorthAndWallBehindThem = createGameState(northFromCenter, "e6h");
    private KuridorGameState withOpponentToSouthAndWallBehindThem = createGameState(southFromCenter, "d3h");
    private KuridorGameState withOpponentToEastAndWallBehindThem = createGameState(eastFromCenter, "f4v");
    private KuridorGameState withOpponentToWestAndWallBehindThem = createGameState(westFromCenter, "c5v");

    private KuridorGameState createGameState(final KuridorGameTeamState secondTeamState, String wall) {
        return KuridorGameState.builder()
                .teams(new HashMap<String, KuridorGameTeamState>() {{
                    put("team_1", centered);
                    put("team_2", secondTeamState);
                }})
                .walls(set(wall))
                .activeTeam("team_1")
                .build();
    }
}
