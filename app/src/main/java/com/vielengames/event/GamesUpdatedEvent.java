package com.vielengames.event;

import com.vielengames.data.Game;

import java.util.List;

import lombok.Value;

@Value
public final class GamesUpdatedEvent {

    List<Game> games;
}
