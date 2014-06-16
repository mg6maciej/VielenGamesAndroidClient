package com.elpassion.vielengames.data.kuridor;

import hrisey.Parcelable;
import lombok.Value;
import lombok.experimental.Builder;

@Parcelable
@Builder
@Value
public final class WallPosition {
    String position;
}
