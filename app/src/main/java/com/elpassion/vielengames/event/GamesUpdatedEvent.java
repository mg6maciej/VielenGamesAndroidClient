package com.elpassion.vielengames.event;

import com.elpassion.vielengames.data.Game;

import java.util.List;

import lombok.Value;

@Value
public final class GamesUpdatedEvent {

    List<Game> games;
}
