package com.vielengames.event;

import com.vielengames.data.Game;

import lombok.Value;

@Value
public final class GameClickEvent {

    Game game;
}
