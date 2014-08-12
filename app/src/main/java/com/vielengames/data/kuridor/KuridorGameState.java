package com.vielengames.data.kuridor;

import com.vielengames.data.Team;
import com.vielengames.utils.Sets;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import hrisey.Parcelable;
import lombok.Value;
import lombok.experimental.Builder;
import lombok.experimental.Wither;

@Parcelable
@Builder
@Value
@Wither
public final class KuridorGameState {

    Map<Team, KuridorGameTeamState> teams;
    Set<String> walls;
    Team activeTeam;

    public String getActiveTeamPawnPosition() {
        return teams.get(activeTeam).getPawnPosition();
    }

    public int getActiveTeamWallsLeft() {
        return teams.get(activeTeam).getWallsLeft();
    }

    public Collection<String> getInactiveTeamsPawnPositions() {
        ArrayList<Team> inactiveTeams = new ArrayList<Team>(teams.keySet());
        inactiveTeams.remove(activeTeam);
        ArrayList<String> pawnPositions = new ArrayList<String>();
        for (Team team : inactiveTeams) {
            pawnPositions.add(teams.get(team).getPawnPosition());
        }
        return pawnPositions;
    }

    public KuridorGameState move(KuridorMove move) {
        if (!isMoveValid(move)) {
            throw new IllegalStateException();
        }
        return new KuridorGameStateMoveUpdater(this, move).getNewState();
    }

    public boolean isMoveValid(KuridorMove move) {
        return new KuridorMoveValidatorImpl(this, move).isMoveValid();
    }

    public Collection<KuridorMove> getLegalPawnMoves() {
        return new LegalPawnMoveGeneratorImpl(this).getLegalPawnMoves();
    }

    public KuridorGameTeamState getFirstTeamState() {
        return teams.get(Team.FIRST);
    }

    public KuridorGameTeamState getSecondTeamState() {
        return teams.get(Team.SECOND);
    }

    public boolean isActive() {
        return activeTeam != null;
    }

    public static KuridorGameState initial() {
        return KuridorGameState.builder()
                .teams(new HashMap<Team, KuridorGameTeamState>() {{
                    put(Team.FIRST, KuridorGameTeamState.builder().pawnPosition("e1").wallsLeft(10).build());
                    put(Team.SECOND, KuridorGameTeamState.builder().pawnPosition("e9").wallsLeft(10).build());
                }})
                .walls(Sets.<String>set())
                .activeTeam(Team.FIRST)
                .build();
    }
}
