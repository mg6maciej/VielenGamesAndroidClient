package com.vielengames.data.kuridor;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class KuridorGameStateMoveUpdater {

    private final KuridorGameState state;
    private final KuridorMove move;

    public KuridorGameState getNewState() {
        Map<String, KuridorGameTeamState> teams = getNewTeams();
        Set<String> walls = getNewWalls();
        String activeTeam = getNewActiveTeam();
        return KuridorGameState.builder()
                .teams(teams)
                .walls(walls)
                .activeTeam(activeTeam)
                .build();
    }

    private Map<String, KuridorGameTeamState> getNewTeams() {
        KuridorGameTeamState teamState = getNewTeamState();
        Map<String, KuridorGameTeamState> newTeams = new HashMap<String, KuridorGameTeamState>(state.getTeams());
        newTeams.put(state.getActiveTeam(), teamState);
        return Collections.unmodifiableMap(newTeams);
    }

    private KuridorGameTeamState getNewTeamState() {
        String pawnPosition = getNewPawnPosition();
        int wallsLeft = getNewWallsLeft();
        return KuridorGameTeamState.builder()
                .pawnPosition(pawnPosition)
                .wallsLeft(wallsLeft)
                .build();
    }

    private String getNewPawnPosition() {
        return move.isPawn()
                ? move.getPosition()
                : state.getActiveTeamPawnPosition();
    }

    private int getNewWallsLeft() {
        int wallsLeft = state.getActiveTeamWallsLeft();
        if (move.isWall()) {
            wallsLeft--;
        }
        return wallsLeft;
    }

    private Set<String> getNewWalls() {
        Set<String> walls = new HashSet<String>(state.getWalls());
        if (move.isWall()) {
            walls.add(move.getPosition());
        }
        return Collections.unmodifiableSet(walls);
    }

    private String getNewActiveTeam() {
        return "team_1".equals(state.getActiveTeam()) ? "team_2" : "team_1";
    }
}
