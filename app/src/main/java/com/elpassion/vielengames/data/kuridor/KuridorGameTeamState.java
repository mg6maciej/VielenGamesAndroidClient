package com.elpassion.vielengames.data.kuridor;

import hrisey.Parcelable;
import lombok.Value;
import lombok.experimental.Builder;

@Parcelable
@Value
@Builder
public final class KuridorGameTeamState {

    String pawnPosition;
    int wallsLeft;
}
