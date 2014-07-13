package com.vielengames.ui;

import android.content.Context;

import com.vielengames.data.Game;
import com.vielengames.data.Player;
import com.vielengames.data.kuridor.KuridorGame;
import com.vielengames.ui.common.AbstractBaseAdapter;

import java.util.ArrayList;
import java.util.List;

public final class GamesAdapter extends AbstractBaseAdapter {

    private Player me;

    public GamesAdapter(Context context, Player me) {
        super(context);
        this.me = me;
    }

    public void updateGames(List<Game> games) {
        List<Game> myTurnGames = new ArrayList<Game>();
        List<Game> recentlyFinishedGames = new ArrayList<Game>();
        List<Game> oppTurnGames = new ArrayList<Game>();
        for (Game game : games) {
            if (imActiveUser(game)) {
                myTurnGames.add(game);
            } else if (noActivePlayer(game)) {
                recentlyFinishedGames.add(game);
            } else {
                oppTurnGames.add(game);
            }
        }
        items.clear();
        addGames("MY TURN", myTurnGames);
        addGames("RECENTLY FINISHED", recentlyFinishedGames);
        addGames("OPPONENT'S TURN", oppTurnGames);
        notifyDataSetChanged();
    }

    private boolean imActiveUser(Game game) {
        return me.equals(((KuridorGame) game).getActivePlayer());
    }

    private boolean noActivePlayer(Game game) {
        return ((KuridorGame) game).getActivePlayer() == null;
    }

    private void addGames(String sectionTitle, List<Game> games) {
        if (games.size() > 0) {
            items.add(new GamesSectionItemAdapter(sectionTitle));
            for (Game game : games) {
                items.add(new GameItemAdapter(game, me));
            }
        }
    }
}
