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
        assertNotValid("e6");
        assertNotValid("f5");
        assertNotValid("e4");
        assertNotValid("d5");
        assertNotValid("a2");
        assertNotValid("b1");
        assertNotValid("c3v");
        assertNotValid("c3h");
    }

    private boolean valid(String position) {
        return testedState.isMoveValid(KuridorMove.pawn(position));
    }

    private void assertNotValid(String position) {
        assertFalse(valid(position));
    }

    private KuridorGameState gameEnded = KuridorGameState.builder()
            .teams(new HashMap<String, KuridorGameTeamState>() {{
                put("team_1", KuridorGameTeamState.builder().pawnPosition("e5").wallsLeft(10).build());
                put("team_2", KuridorGameTeamState.builder().pawnPosition("a1").wallsLeft(10).build());
            }})
            .walls(Collections.<String>emptyList())
            .build();
}
