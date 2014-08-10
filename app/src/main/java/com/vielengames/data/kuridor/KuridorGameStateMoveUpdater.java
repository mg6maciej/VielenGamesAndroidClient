package com.vielengames.data.kuridor;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.NONE, makeFinal = true)
@RequiredArgsConstructor
public final class KuridorGameStateMoveUpdater {

    KuridorGameState state;
    KuridorMove move;

    public KuridorGameState getNewState() {
        String activeTeamPawnPosition = state.getActiveTeamPawnPosition();
        if (move.isPawn()) {
            activeTeamPawnPosition = move.getPosition();
        }
        int activeTeamWallsLeft = state.getActiveTeamWallsLeft();
        if (move.isWall()) {
            activeTeamWallsLeft--;
        }
        final KuridorGameTeamState newTeamState = KuridorGameTeamState.builder()
                .pawnPosition(activeTeamPawnPosition)
                .wallsLeft(activeTeamWallsLeft)
                .build();
        HashMap<String, KuridorGameTeamState> newTeams = new HashMap<String, KuridorGameTeamState>(state.getTeams());
        newTeams.put(state.getActiveTeam(), newTeamState);
        KuridorGameState newState = state.withTeams(Collections.unmodifiableMap(newTeams));
        if (move.isWall()) {
            Set<String> newWalls = new HashSet<String>(state.getWalls());
            newWalls.add(move.getPosition());
            newState = newState.withWalls(Collections.unmodifiableSet(newWalls));
        }
        String newActiveTeam = "team_1".equals(state.getActiveTeam()) ? "team_2" : "team_1";
        return newState.withActiveTeam(newActiveTeam);
    }
}
