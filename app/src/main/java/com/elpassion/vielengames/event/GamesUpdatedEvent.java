package com.elpassion.vielengames.event;

import com.elpassion.vielengames.data.Game;

import java.util.Set;

import lombok.Value;

@Value
public final class GamesUpdatedEvent {

    Set<Game> games;
}
