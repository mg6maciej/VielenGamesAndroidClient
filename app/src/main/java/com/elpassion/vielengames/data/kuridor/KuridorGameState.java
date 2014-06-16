package com.elpassion.vielengames.data.kuridor;

import java.util.List;

import hrisey.Parcelable;
import lombok.Value;

@Parcelable
@Value
public final class KuridorGameState {

    List<PawnPosition> pawns;
    List<WallPosition> walls;
}
