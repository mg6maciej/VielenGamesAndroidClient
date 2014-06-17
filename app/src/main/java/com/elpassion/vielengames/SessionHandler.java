package com.elpassion.vielengames;

import com.elpassion.vielengames.api.VielenGamesClient;
import com.elpassion.vielengames.data.Game;
import com.elpassion.vielengames.data.SessionResponse;
import com.elpassion.vielengames.data.Updates;
import com.elpassion.vielengames.event.GamesUpdatedEvent;
import com.elpassion.vielengames.event.SessionResponseEvent;
import com.elpassion.vielengames.event.UpdatesEvent;
import com.elpassion.vielengames.event.bus.EventBus;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public final class SessionHandler {

    private final VielenGamesClient client;
    private final EventBus eventBus;

    private String timestamp;
    private Set<Game> games;

    @Inject
    public SessionHandler(VielenGamesClient client, EventBus eventBus) {
        this.client = client;
        this.eventBus = eventBus;
        this.eventBus.register(this);
    }

    public Set<Game> getGames() {
        return games;
    }

    @SuppressWarnings("unused")
    public void onEvent(SessionResponseEvent event) {
        SessionResponse sessionResponse = event.getSessionResponse();
        this.games = new HashSet<Game>();
        storeUpdates(sessionResponse.getUpdates());
    }

    private void requestUpdates() {
        client.requestUpdates(timestamp);
    }

    @SuppressWarnings("unused")
    public void onEvent(UpdatesEvent event) {
        storeUpdates(event.getUpdates());
    }

    private void storeUpdates(Updates updates) {
        timestamp = updates.getTimestamp();
        games.addAll(updates.getGames());
        eventBus.post(new GamesUpdatedEvent(games));
        requestUpdates();
    }
}
