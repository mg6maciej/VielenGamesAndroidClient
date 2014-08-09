package com.vielengames.data.kuridor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public final class KuridorMoveImpl {

    private KuridorMoveImpl() {
    }

    public static KuridorGameState move(final KuridorGameState state, KuridorMove move) {
        String activeTeamPawnPosition = state.getActiveTeamPawnPosition();
        if (move.getMoveType() == KuridorMove.MoveType.pawn) {
            activeTeamPawnPosition = move.getPosition();
        }
        int activeTeamWallsLeft = state.getActiveTeamWallsLeft();
        if (move.getMoveType() == KuridorMove.MoveType.wall) {
            activeTeamWallsLeft--;
        }
        final KuridorGameTeamState newTeamState = KuridorGameTeamState.builder()
                .pawnPosition(activeTeamPawnPosition)
                .wallsLeft(activeTeamWallsLeft)
                .build();
        KuridorGameState newState = state.withTeams(new HashMap<String, KuridorGameTeamState>(state.getTeams()) {{
            put(state.getActiveTeam(), newTeamState);
        }});
        if (move.getMoveType() == KuridorMove.MoveType.wall) {
            List<String> newWalls = new ArrayList<String>(state.getWalls());
            newWalls.add(move.getPosition());
            newState = newState.withWalls(Collections.unmodifiableList(newWalls));
        }
        String newActiveTeam = "team_1".equals(state.getActiveTeam()) ? "team_2" : "team_1";
        return newState.withActiveTeam(newActiveTeam);
    }
}
