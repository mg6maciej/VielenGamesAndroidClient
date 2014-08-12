package com.vielengames.data.kuridor;

import com.vielengames.data.Team;

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
        Map<Team, KuridorGameTeamState> teams = getNewTeams();
        Set<String> walls = getNewWalls();
        Team activeTeam = getNewActiveTeam();
        return KuridorGameState.builder()
                .teams(teams)
                .walls(walls)
                .activeTeam(activeTeam)
                .build();
    }

    private Map<Team, KuridorGameTeamState> getNewTeams() {
        KuridorGameTeamState teamState = getNewTeamState();
        Map<Team, KuridorGameTeamState> newTeams = new HashMap<Team, KuridorGameTeamState>(state.getTeams());
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

    private Team getNewActiveTeam() {
        return Team.FIRST.equals(state.getActiveTeam()) ? Team.SECOND : Team.FIRST;
    }
}
