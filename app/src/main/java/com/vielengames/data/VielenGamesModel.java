package com.vielengames.data;

import com.vielengames.event.GamesUpdatedEvent;
import com.vielengames.event.SessionUpdatesResponseEvent;
import com.vielengames.event.bus.EventBus;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public final class VielenGamesModel {

    private final EventBus eventBus;
    private List<Game> games = new ArrayList<Game>();

    @Inject
    public VielenGamesModel(EventBus eventBus) {
        this.eventBus = eventBus;
        eventBus.register(this);
    }

    public List<Game> getMyGames() {
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
    public void onEvent(SessionUpdatesResponseEvent event) {
        syncState(event.getUpdates());
    }

    private void syncState(Updates updates) {
        syncGames(updates.getGames());
    }

    private void syncGames(List<Game> games) {
        if (games.isEmpty()) {
            return;
        }
        this.games.removeAll(games);
        this.games.addAll(0, games);
        eventBus.post(new GamesUpdatedEvent(games));
    }
}
