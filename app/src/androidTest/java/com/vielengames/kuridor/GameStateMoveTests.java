package com.vielengames.kuridor;

import com.vielengames.data.kuridor.KuridorGameState;
import com.vielengames.data.kuridor.KuridorMove;

import junit.framework.TestCase;

import java.util.Collection;

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

    public void testWallMoveAddsToList() {
        KuridorGameState testedState = KuridorGameState.initial();
        KuridorGameState next = testedState.move(KuridorMove.wall("e8h"));
        Collection<String> walls = next.getWalls();
        assertEquals(1, walls.size());
        assertEquals("e8h", walls.iterator().next());
    }
}
