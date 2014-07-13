package com.vielengames.kuridor;

import com.vielengames.data.kuridor.KuridorGameState;
import com.vielengames.data.kuridor.KuridorGameTeamState;

import java.util.Collections;
import java.util.HashMap;

public final class PawnMoveValidationEndOfBoardJumpTests extends PawnMoveValidationBaseTestCase {

    public void testCanJumpLeftOrRightOverOppToNorthAndEndOfBoardBehindThem() {
        testedState = withPawnsCloseToNorthEndOfBoard;
        assertValid("d9");
        assertValid("f9");
    }

    public void testCanJumpLeftOrRightOverOppToSouthAndEndOfBoardBehindThem() {
        testedState = withPawnsCloseToSouthEndOfBoard;
        assertValid("d1");
        assertValid("f1");
    }

    public void testCanJumpLeftOrRightOverOppToEastAndEndOfBoardBehindThem() {
        testedState = withPawnsCloseToEastEndOfBoard;
        assertValid("a4");
        assertValid("a6");
    }

    public void testCanJumpLeftOrRightOverOppToWestAndEndOfBoardBehindThem() {
        testedState = withPawnsCloseToWestEndOfBoard;
        assertValid("i1");
        assertValid("i3");
    }

    private KuridorGameState withPawnsCloseToNorthEndOfBoard = createGameState("e8", "e9", "team_1");
    private KuridorGameState withPawnsCloseToSouthEndOfBoard = createGameState("e1", "e2", "team_2");
    private KuridorGameState withPawnsCloseToEastEndOfBoard = createGameState("b5", "a5", "team_1");
    private KuridorGameState withPawnsCloseToWestEndOfBoard = createGameState("h2", "i2", "team_1");

    private KuridorGameState createGameState(final String firstTeamPawnPosition, final String secondTeamPawnPosition, String activeTeam) {
        return KuridorGameState.builder()
                .teams(new HashMap<String, KuridorGameTeamState>() {{
                    put("team_1", KuridorGameTeamState.builder().pawnPosition(firstTeamPawnPosition).wallsLeft(10).build());
                    put("team_2", KuridorGameTeamState.builder().pawnPosition(secondTeamPawnPosition).wallsLeft(10).build());
                }})
                .walls(Collections.<String>emptyList())
                .activeTeam(activeTeam)
                .build();
    }
}
