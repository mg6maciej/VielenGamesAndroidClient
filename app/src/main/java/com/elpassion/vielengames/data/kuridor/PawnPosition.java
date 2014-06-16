package com.elpassion.vielengames.data.kuridor;

import lombok.Value;
import lombok.experimental.Builder;

@Builder
@Value
public final class PawnPosition {
    String position;
    String team;
}
