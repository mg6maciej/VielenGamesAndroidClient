package com.vielengames.kuridor;

import com.vielengames.data.Team;
import com.vielengames.data.kuridor.KuridorGameState;
import com.vielengames.data.kuridor.KuridorGameTeamState;
import com.vielengames.data.kuridor.KuridorMove;

import junit.framework.TestCase;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

public final class GameStateMoveTests extends TestCase {

    public void testMoveChangesActiveTeam() {
        KuridorGameState testedState = KuridorGameState.initial();
        KuridorGameState next = testedState.move(KuridorMove.pawn("e2"));
        assertEquals(Team.SECOND, next.getActiveTeam());
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

    public void testWallMoveDecrementsCount() {
        KuridorGameState testedState = KuridorGameState.initial();
        KuridorGameState next = testedState.move(KuridorMove.wall("e8h"));
        assertEquals(9, next.getTeam1().getWallsLeft());
    }

    public void testFewMoves() {
        KuridorGameState actual = KuridorGameState.initial()
                .move(KuridorMove.pawn("e2"))
                .move(KuridorMove.wall("e2h"))
                .move(KuridorMove.pawn("d2"))
                .move(KuridorMove.pawn("e8"))
                .move(KuridorMove.wall("e7v"))
                .move(KuridorMove.wall("c2h"));
        KuridorGameState expected = KuridorGameState.builder()
                .teams(new HashMap<Team, KuridorGameTeamState>() {{
                    put(Team.FIRST, KuridorGameTeamState.builder().pawnPosition("d2").wallsLeft(9).build());
                    put(Team.SECOND, KuridorGameTeamState.builder().pawnPosition("e8").wallsLeft(8).build());
                }})
                .walls(new HashSet<String>(Arrays.asList("c2h", "e2h", "e7v")))
                .activeTeam(Team.FIRST)
                .build();
        assertEquals(expected, actual);
    }
}
