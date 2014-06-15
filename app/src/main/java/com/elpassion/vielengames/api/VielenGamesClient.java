package com.elpassion.vielengames.api;

import com.elpassion.vielengames.event.bus.EventBus;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public final class VielenGamesClient {

    EventBus eventBus;
    VielenGamesAuthApi authApi;
    VielenGamesApi api;
}
