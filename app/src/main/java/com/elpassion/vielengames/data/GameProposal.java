package com.elpassion.vielengames.data;

import java.util.List;

import hrisey.Parcelable;
import lombok.Value;
import lombok.experimental.Builder;

@Parcelable
@Value
@Builder
public final class GameProposal {

    String id;
    String gameType;
    List<Player> awaitingPlayers;
    int ageInSeconds;
}
