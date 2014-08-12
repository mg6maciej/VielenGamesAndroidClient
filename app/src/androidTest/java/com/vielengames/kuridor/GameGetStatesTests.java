package com.vielengames.kuridor;

import com.vielengames.data.kuridor.KuridorGame;
import com.vielengames.data.kuridor.KuridorGameState;
import com.vielengames.data.kuridor.KuridorGameTeamState;
import com.vielengames.data.kuridor.KuridorMove;

import junit.framework.TestCase;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public final class GameGetStatesTests extends TestCase {

    public void testInitialState() {
        KuridorGame game = KuridorGame.builder()
                .moves(Collections.<KuridorMove>emptyList())
                .build();
        List<KuridorGameState> states = game.getStates();
        assertEquals(1, states.size());
        assertEquals(KuridorGameState.initial(), states.get(0));
    }

    public void testAfterMove() {
        KuridorGame game = KuridorGame.builder()
                .moves(Collections.singletonList(KuridorMove.pawn("e2")))
                .build();
        List<KuridorGameState> states = game.getStates();
        assertEquals(2, states.size());
        KuridorGameState expected = KuridorGameState.builder()
                .teams(new HashMap<String, KuridorGameTeamState>() {{
                    put("team_1", KuridorGameTeamState.builder().pawnPosition("e2").wallsLeft(10).build());
                    put("team_2", KuridorGameTeamState.builder().pawnPosition("e9").wallsLeft(10).build());
                }})
                .walls(Collections.<String>emptySet())
                .activeTeam("team_2")
                .build();
        assertEquals(expected, states.get(1));
    }
}
