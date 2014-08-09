package com.vielengames.data.kuridor;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

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
        HashMap<String, KuridorGameTeamState> newTeams = new HashMap<String, KuridorGameTeamState>(state.getTeams());
        newTeams.put(state.getActiveTeam(), newTeamState);
        KuridorGameState newState = state.withTeams(Collections.unmodifiableMap(newTeams));
        if (move.getMoveType() == KuridorMove.MoveType.wall) {
            Set<String> newWalls = new HashSet<String>(state.getWalls());
            newWalls.add(move.getPosition());
            newState = newState.withWalls(Collections.unmodifiableSet(newWalls));
        }
        String newActiveTeam = "team_1".equals(state.getActiveTeam()) ? "team_2" : "team_1";
        return newState.withActiveTeam(newActiveTeam);
    }
}
