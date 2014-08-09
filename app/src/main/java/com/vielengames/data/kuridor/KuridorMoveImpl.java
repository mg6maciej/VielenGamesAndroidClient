package com.vielengames.data.kuridor;

import java.util.HashMap;

public final class KuridorMoveImpl {

    private KuridorMoveImpl() {
    }

    public static KuridorGameState move(final KuridorGameState state, final KuridorMove move) {
        KuridorGameState newState = state.withTeams(new HashMap<String, KuridorGameTeamState>(state.getTeams()) {{
            put(state.getActiveTeam(), KuridorGameTeamState.builder().pawnPosition(move.getPosition()).wallsLeft(state.getActiveTeamWallsLeft()).build());
        }});
        String newActiveTeam = "team_1".equals(state.getActiveTeam()) ? "team_2" : "team_1";
        return newState.withActiveTeam(newActiveTeam);
    }
}
