package com.vielengames.kuridor;

import com.vielengames.data.kuridor.KuridorGame;
import com.vielengames.data.kuridor.KuridorGameState;
import com.vielengames.data.kuridor.KuridorMove;

import junit.framework.TestCase;

import java.util.Collections;
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
}
