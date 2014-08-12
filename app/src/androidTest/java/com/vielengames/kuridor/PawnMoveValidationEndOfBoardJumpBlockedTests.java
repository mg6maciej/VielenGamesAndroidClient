package com.vielengames.kuridor;

import com.vielengames.data.Team;
import com.vielengames.data.kuridor.KuridorGameState;
import com.vielengames.data.kuridor.KuridorGameTeamState;

import java.util.HashMap;

import static com.vielengames.utils.Sets.set;

public final class PawnMoveValidationEndOfBoardJumpBlockedTests extends PawnMoveValidationBaseTestCase {

    public void testCanJumpLeftOrRightOverOppToNorthAndEndOfBoardBehindThem() {
        testedState = withPawnsCloseToNorthEndOfBoardAndWallsAround;
        assertNotValid("d9");
        assertNotValid("f9");
    }

    public void testCanJumpLeftOrRightOverOppToSouthAndEndOfBoardBehindThem() {
        testedState = withPawnsCloseToSouthEndOfBoardAndWallsAround;
        assertNotValid("d1");
        assertNotValid("f1");
    }

    public void testCanJumpLeftOrRightOverOppToEastAndEndOfBoardBehindThem() {
        testedState = withPawnsCloseToEastEndOfBoardAndWallsAround;
        assertNotValid("a4");
        assertNotValid("a6");
    }

    public void testCanJumpLeftOrRightOverOppToWestAndEndOfBoardBehindThem() {
        testedState = withPawnsCloseToWestEndOfBoardAndWallsAround;
        assertNotValid("i1");
        assertNotValid("i3");
    }

    private KuridorGameState withPawnsCloseToNorthEndOfBoardAndWallsAround = createGameState("e8", "e9", Team.FIRST, "e8v", "d8v");
    private KuridorGameState withPawnsCloseToSouthEndOfBoardAndWallsAround = createGameState("e1", "e2", Team.SECOND, "e1v", "d1v");
    private KuridorGameState withPawnsCloseToEastEndOfBoardAndWallsAround = createGameState("b5", "a5", Team.FIRST, "a5h", "a4h");
    private KuridorGameState withPawnsCloseToWestEndOfBoardAndWallsAround = createGameState("h2", "i2", Team.FIRST, "h2h", "h1h");

    private KuridorGameState createGameState(final String firstTeamPawnPosition, final String secondTeamPawnPosition, Team activeTeam, String... walls) {
        return KuridorGameState.builder()
                .teams(new HashMap<Team, KuridorGameTeamState>() {{
                    put(Team.FIRST, KuridorGameTeamState.builder().pawnPosition(firstTeamPawnPosition).wallsLeft(10).build());
                    put(Team.SECOND, KuridorGameTeamState.builder().pawnPosition(secondTeamPawnPosition).wallsLeft(10).build());
                }})
                .walls(set(walls))
                .activeTeam(activeTeam)
                .build();
    }
}
