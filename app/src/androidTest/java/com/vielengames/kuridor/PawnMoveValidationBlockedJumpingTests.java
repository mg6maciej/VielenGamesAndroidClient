package com.vielengames.kuridor;

import com.vielengames.data.kuridor.KuridorGameState;
import com.vielengames.data.kuridor.KuridorGameTeamState;

import java.util.HashMap;

import static com.vielengames.utils.Sets.set;

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

    private KuridorGameState withOpponentToNorthAndWallBetweenUs = createGameState(northFromCenter, "e5h");
    private KuridorGameState withOpponentToSouthAndWallBetweenUs = createGameState(southFromCenter, "d4h");
    private KuridorGameState withOpponentToEastAndWallBetweenUs = createGameState(eastFromCenter, "e4v");
    private KuridorGameState withOpponentToWestAndWallBetweenUs = createGameState(westFromCenter, "d5v");
    private KuridorGameState withOpponentToNorthAndWallBehindThem = createGameState(northFromCenter, "d6h");
    private KuridorGameState withOpponentToSouthAndWallBehindThem = createGameState(southFromCenter, "e3h");
    private KuridorGameState withOpponentToEastAndWallBehindThem = createGameState(eastFromCenter, "f5v");
    private KuridorGameState withOpponentToWestAndWallBehindThem = createGameState(westFromCenter, "c4v");

    private KuridorGameState createGameState(final KuridorGameTeamState secondTeamState, String wall) {
        return KuridorGameState.builder()
                .teams(new HashMap<String, KuridorGameTeamState>() {{
                    put("team_1", centered);
                    put("team-2", secondTeamState);
                }})
                .walls(set(wall))
                .activeTeam("team_1")
                .build();
    }
}
