package com.vielengames.data.kuridor;

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

import static com.vielengames.utils.Sets.set;

@Parcelable
@Builder
@Value
@Wither
public final class KuridorGameState {

    Map<String, KuridorGameTeamState> teams;
    Set<String> walls;
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

    public KuridorGameState move(KuridorMove move) {
        if (!isMoveValid(move)) {
            throw new IllegalStateException();
        }
        return new KuridorGameStateMoveUpdater(this, move).getNewState();
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
                .walls(Sets.<String>set())
                .activeTeam("team_1")
                .build();
    }
}
