package com.elpassion.vielengames.data;

import com.elpassion.vielengames.data.kuridor.KuridorGame;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public final class VielenGamesModel {

    @Inject
    public VielenGamesModel() {
    }

    public Set<Game> getMyGames() {
        return new HashSet<Game>();
    }

    public <T extends Game> T getGameById(String gameId) {
        return null;
    }
}
