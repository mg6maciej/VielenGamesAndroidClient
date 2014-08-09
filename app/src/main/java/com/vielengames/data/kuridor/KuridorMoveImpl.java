package com.vielengames.data.kuridor;

public final class KuridorMoveImpl {

    private KuridorMoveImpl() {
    }

    public static KuridorGameState move(KuridorGameState state, KuridorMove move) {
        String newActiveTeam = "team_1".equals(state.getActiveTeam()) ? "team_2" : "team_1";
        return state.withActiveTeam(newActiveTeam);
    }
}
