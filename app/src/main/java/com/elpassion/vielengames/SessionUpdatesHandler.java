package com.elpassion.vielengames;

import android.os.Handler;

import com.elpassion.vielengames.api.VielenGamesClient;
import com.elpassion.vielengames.data.Game;
import com.elpassion.vielengames.data.SessionResponse;
import com.elpassion.vielengames.data.Updates;
import com.elpassion.vielengames.event.GamesUpdatedEvent;
import com.elpassion.vielengames.event.SessionStartedResponseEvent;
import com.elpassion.vielengames.event.SessionUpdatesResponseEvent;
import com.elpassion.vielengames.event.bus.EventBus;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public final class SessionUpdatesHandler {

    private final VielenGamesClient client;
    private final EventBus eventBus;
    private final Handler handler = new Handler();

    private String timestamp;
    private Set<Game> games;

    @Inject
    public SessionUpdatesHandler(VielenGamesClient client, EventBus eventBus) {
        this.client = client;
        this.eventBus = eventBus;
        this.eventBus.register(this);
    }

    public Set<Game> getGames() {
        return games;
    }

    @SuppressWarnings("unused")
    public void onEvent(SessionStartedResponseEvent event) {
        SessionResponse sessionResponse = event.getSessionResponse();
        this.games = new HashSet<Game>();
        storeUpdates(sessionResponse.getUpdates());
    }

    private void requestUpdates() {
        client.requestUpdates(timestamp);
    }

    @SuppressWarnings("unused")
    public void onEvent(SessionUpdatesResponseEvent event) {
        storeUpdates(event.getUpdates());
    }

    private void storeUpdates(Updates updates) {
        timestamp = updates.getUntil();
        games.addAll(updates.getGames());
        eventBus.post(new GamesUpdatedEvent(games));
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                requestUpdates();
            }
        }, 10000);
    }
}
