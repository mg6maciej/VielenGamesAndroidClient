package com.elpassion.vielengames.kuridor;

import com.elpassion.vielengames.data.kuridor.KuridorGameState;
import com.elpassion.vielengames.data.kuridor.KuridorGameTeamState;
import com.elpassion.vielengames.data.kuridor.KuridorMove;

import junit.framework.TestCase;

public abstract class PawnMoveValidationBaseTestCase extends TestCase {

    KuridorGameState testedState;

    KuridorGameTeamState centered = KuridorGameTeamState.builder()
            .pawnPosition("e5")
            .wallsLeft(10)
            .build();
    KuridorGameTeamState secondStarting = KuridorGameTeamState.builder()
            .pawnPosition("e9")
            .wallsLeft(10)
            .build();
    KuridorGameTeamState northFromCenter = KuridorGameTeamState.builder()
            .pawnPosition("e6")
            .wallsLeft(10)
            .build();
    KuridorGameTeamState southFromCenter = KuridorGameTeamState.builder()
            .pawnPosition("e4")
            .wallsLeft(10)
            .build();
    KuridorGameTeamState eastFromCenter = KuridorGameTeamState.builder()
            .pawnPosition("f5")
            .wallsLeft(10)
            .build();
    KuridorGameTeamState westFromCenter = KuridorGameTeamState.builder()
            .pawnPosition("d5")
            .wallsLeft(10)
            .build();

    boolean valid(String position) {
        return testedState.isMoveValid(KuridorMove.pawn(position));
    }

    void assertValid(String position) {
        assertTrue(valid(position));
    }

    void assertNotValid(String position) {
        assertFalse(valid(position));
    }
}
