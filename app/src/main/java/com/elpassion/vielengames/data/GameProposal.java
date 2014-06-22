package com.elpassion.vielengames.data;

import android.os.SystemClock;

import java.util.List;

import hrisey.Parcelable;
import lombok.Value;
import lombok.experimental.Builder;
import lombok.experimental.NonFinal;

@Parcelable
@Value
@Builder
public final class GameProposal {

    String id;
    String gameType;
    List<Player> awaitingPlayers;
    int ageInSeconds;
    @NonFinal
    Long creationTime;

    public void updateCreationTime() {
        creationTime = SystemClock.elapsedRealtime();
    }

    public int getAgeInSeconds() {
        if (creationTime == null) {
            throw new IllegalStateException();
        }
        return ageInSeconds + (int) ((SystemClock.elapsedRealtime() - creationTime) / 1000);
    }
}
