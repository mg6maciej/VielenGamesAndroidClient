package com.vielengames.kuridor;

import com.vielengames.data.kuridor.KuridorGameState;
import com.vielengames.data.kuridor.KuridorGameTeamState;
import com.vielengames.data.kuridor.KuridorMove;

import junit.framework.TestCase;

import java.util.Collections;
import java.util.HashMap;

public final class MoveValidationGameEndedTests extends TestCase {

    private KuridorGameState testedState;

    public void testNoMoveLegalWhenGameEnded() {
        testedState = gameEnded;
        assertPawnMoveNotValid("e6");
        assertPawnMoveNotValid("f5");
        assertPawnMoveNotValid("e4");
        assertPawnMoveNotValid("d5");
        assertPawnMoveNotValid("a2");
        assertPawnMoveNotValid("b1");
        assertWallMoveNotValid("c3v");
        assertWallMoveNotValid("c3h");
    }

    private boolean pawnMoveValid(String position) {
        return testedState.isMoveValid(KuridorMove.pawn(position));
    }

    private boolean wallMoveValid(String position) {
        return testedState.isMoveValid(KuridorMove.wall(position));
    }

    private void assertPawnMoveNotValid(String position) {
        assertFalse(pawnMoveValid(position));
    }

    private void assertWallMoveNotValid(String position) {
        assertFalse(wallMoveValid(position));
    }

    private KuridorGameState gameEnded = KuridorGameState.builder()
            .teams(new HashMap<String, KuridorGameTeamState>() {{
                put("team_1", KuridorGameTeamState.builder().pawnPosition("e5").wallsLeft(10).build());
                put("team_2", KuridorGameTeamState.builder().pawnPosition("a1").wallsLeft(10).build());
            }})
            .walls(Collections.<String>emptyList())
            .activeTeam(null)
            .build();
}
