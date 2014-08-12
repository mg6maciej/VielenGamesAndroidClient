package com.vielengames.kuridor;

import com.vielengames.data.Team;
import com.vielengames.data.kuridor.KuridorGameState;
import com.vielengames.data.kuridor.KuridorGameTeamState;
import com.vielengames.utils.Sets;

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

    private KuridorGameState withPawnsCloseToNorthEndOfBoard = createGameState("e8", "e9", Team.FIRST);
    private KuridorGameState withPawnsCloseToSouthEndOfBoard = createGameState("e1", "e2", Team.SECOND);
    private KuridorGameState withPawnsCloseToEastEndOfBoard = createGameState("b5", "a5", Team.FIRST);
    private KuridorGameState withPawnsCloseToWestEndOfBoard = createGameState("h2", "i2", Team.FIRST);

    private KuridorGameState createGameState(final String firstTeamPawnPosition, final String secondTeamPawnPosition, Team activeTeam) {
        return KuridorGameState.builder()
                .teams(new HashMap<Team, KuridorGameTeamState>() {{
                    put(Team.FIRST, KuridorGameTeamState.builder().pawnPosition(firstTeamPawnPosition).wallsLeft(10).build());
                    put(Team.SECOND, KuridorGameTeamState.builder().pawnPosition(secondTeamPawnPosition).wallsLeft(10).build());
                }})
                .walls(Sets.<String>set())
                .activeTeam(activeTeam)
                .build();
    }
}
