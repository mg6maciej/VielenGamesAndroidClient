package com.elpassion.vielengames.data.kuridor;

import java.util.ArrayList;
import java.util.List;

import hrisey.Parcelable;
import lombok.Value;
import lombok.experimental.Builder;

@Parcelable
@Builder
@Value
public final class KuridorGameState {
    List<PawnPosition> pawns;
    List<WallPosition> walls;

    public static KuridorGameState initial() {

        List<PawnPosition> newPawns = new ArrayList<PawnPosition>(2);
        newPawns.add(PawnPosition.builder().position("e1").team("team_1").build());
        newPawns.add(PawnPosition.builder().position("e9").team("team_2").build());

        return KuridorGameState.builder().
                pawns(newPawns).
                walls(new ArrayList<WallPosition>()).build();
    }
}
