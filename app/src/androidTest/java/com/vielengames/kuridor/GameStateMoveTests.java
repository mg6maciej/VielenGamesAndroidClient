package com.vielengames.kuridor;

import com.vielengames.data.kuridor.KuridorGameState;
import com.vielengames.data.kuridor.KuridorMove;

import junit.framework.TestCase;

public final class GameStateMoveTests extends TestCase {

    public void testMoveChangesActiveTeam() {
        KuridorGameState testedState = KuridorGameState.initial();
        KuridorGameState next = testedState.move(KuridorMove.pawn("e2"));
        assertEquals("team_2", next.getActiveTeam());
    }

    public void testMoveUpdatesPawnPosition() {
        KuridorGameState testedState = KuridorGameState.initial();
        KuridorGameState next = testedState.move(KuridorMove.pawn("e2"));
        assertEquals("e2", next.getTeam1().getPawnPosition());
    }
}
