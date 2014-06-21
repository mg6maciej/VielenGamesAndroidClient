package com.elpassion.vielengames.data.kuridor;

import com.google.gson.annotations.SerializedName;

import java.util.Collection;
import java.util.Collections;

import hrisey.Parcelable;
import lombok.Value;
import lombok.experimental.Builder;

@Parcelable
@Builder
@Value
public final class KuridorGameState {

    @SerializedName("team_1")
    KuridorGameTeamState team1;
    @SerializedName("team_2")
    KuridorGameTeamState team2;
    Collection<String> walls;
    String activeTeam;

    public String getActiveTeamPawnPosition() {
        return "team_1".equals(activeTeam)
                ? team1.getPawnPosition()
                : team2.getPawnPosition();
    }

    public int getActiveTeamWallsLeft() {
        return "team_1".equals(activeTeam)
                ? team1.getWallsLeft()
                : team2.getWallsLeft();
    }

    public Collection<String> getInactiveTeamsPawnPositions() {
        return "team_1".equals(activeTeam)
                ? Collections.singleton(team2.getPawnPosition())
                : Collections.singleton(team1.getPawnPosition());
    }

    public boolean isMoveValid(KuridorMove move) {
        return KuridorMoveValidatorImpl.isMoveValid(this, move);
    }

    public static KuridorGameState initial() {
        return KuridorGameState.builder()
                .team1(KuridorGameTeamState.builder().pawnPosition("e1").wallsLeft(10).build())
                .team2(KuridorGameTeamState.builder().pawnPosition("e9").wallsLeft(10).build())
                .walls(Collections.<String>emptyList())
                .activeTeam("team_1")
                .build();
    }
}
