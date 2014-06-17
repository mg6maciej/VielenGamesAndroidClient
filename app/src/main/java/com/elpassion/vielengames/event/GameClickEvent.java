package com.elpassion.vielengames.event;

import com.elpassion.vielengames.data.Game;

import lombok.Value;

@Value
public final class GameClickEvent {

    Game game;
}
