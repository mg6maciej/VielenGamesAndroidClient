package com.vielengames.data.kuridor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import hrisey.Parcelable;
import lombok.Value;
import lombok.experimental.Builder;
import lombok.experimental.Wither;

@Parcelable
@Builder
@Value
@Wither
public final class KuridorGameState {

    Map<String, KuridorGameTeamState> teams;
    Collection<String> walls;
    String activeTeam;

    public String getActiveTeamPawnPosition() {
        return teams.get(activeTeam).getPawnPosition();
    }

    public int getActiveTeamWallsLeft() {
        return teams.get(activeTeam).getWallsLeft();
    }

    public Collection<String> getInactiveTeamsPawnPositions() {
        ArrayList<String> inactiveTeams = new ArrayList<String>(teams.keySet());
        inactiveTeams.remove(activeTeam);
        ArrayList<String> pawnPositions = new ArrayList<String>();
        for (String team : inactiveTeams) {
            pawnPositions.add(teams.get(team).getPawnPosition());
        }
        return pawnPositions;
    }

    public boolean isMoveValid(KuridorMove move) {
        return KuridorMoveValidatorImpl.isMoveValid(this, move);
    }

    public Collection<KuridorMove> getLegalPawnMoves() {
        return LegalPawnMoveGeneratorImpl.getLegalPawnMoves(this);
    }

    public KuridorGameTeamState getTeam1() {
        return teams.get("team_1");
    }

    public KuridorGameTeamState getTeam2() {
        return teams.get("team_2");
    }

    public static KuridorGameState initial() {
        return KuridorGameState.builder()
                .teams(new HashMap<String, KuridorGameTeamState>() {{
                    put("team_1", KuridorGameTeamState.builder().pawnPosition("e1").wallsLeft(10).build());
                    put("team_2", KuridorGameTeamState.builder().pawnPosition("e9").wallsLeft(10).build());
                }})
                .walls(Collections.<String>emptyList())
                .activeTeam("team_1")
                .build();
    }
}
