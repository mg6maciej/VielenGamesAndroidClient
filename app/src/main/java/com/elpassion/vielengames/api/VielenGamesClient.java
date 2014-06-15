package com.elpassion.vielengames.api;

import com.elpassion.vielengames.event.bus.EventBus;

public final class VielenGamesClient {

    private final EventBus eventBus;
    private final VielenGamesApi api;

    public VielenGamesClient(EventBus eventBus, VielenGamesApi api) {
        this.eventBus = eventBus;
        this.api = api;
    }
}
