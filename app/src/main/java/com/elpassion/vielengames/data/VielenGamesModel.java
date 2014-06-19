package com.elpassion.vielengames.data;

import com.elpassion.vielengames.event.GamesUpdatedEvent;
import com.elpassion.vielengames.event.SessionStartedResponseEvent;
import com.elpassion.vielengames.event.SessionUpdatesResponseEvent;
import com.elpassion.vielengames.event.bus.EventBus;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public final class VielenGamesModel {

    private final EventBus eventBus;
    private Set<Game> games;

    @Inject
    public VielenGamesModel(EventBus eventBus) {
        this.eventBus = eventBus;
        eventBus.register(this);
    }

    public Set<Game> getMyGames() {
        return games;
    }

    public <T extends Game> T getGameById(String gameId) {
        for (Game game : games) {
            if (gameId.equals(game.getId())) {
                return (T) game;
            }
        }
        return null;
    }

    @SuppressWarnings("unused")
    public void onEvent(SessionStartedResponseEvent event) {
        this.games = null;
        syncState(event.getSessionResponse().getUpdates());
    }

    @SuppressWarnings("unused")
    public void onEvent(SessionUpdatesResponseEvent event) {
        syncState(event.getUpdates());
    }

    private void syncState(Updates updates) {
        syncGames(updates.getGames());
    }

    private void syncGames(List<Game> games) {
        if (this.games == null) {
            this.games = new HashSet<Game>();
        }
        this.games.addAll(games);
        eventBus.post(new GamesUpdatedEvent());
    }
}
