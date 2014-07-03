package com.elpassion.vielengames.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.elpassion.vielengames.R;
import com.elpassion.vielengames.data.Game;
import com.elpassion.vielengames.data.Player;
import com.elpassion.vielengames.data.kuridor.KuridorGame;
import com.elpassion.vielengames.data.kuridor.KuridorMove;
import com.elpassion.vielengames.ui.common.AbstractBaseAdapter;
import com.elpassion.vielengames.utils.Circlifier;
import com.elpassion.vielengames.utils.ViewUtils;
import com.elpassion.vielengames.utils.kuridor.KuridorGameStateDrawer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public final class GamesAdapter extends AbstractBaseAdapter {

    private Player me;

    public GamesAdapter(Context context, Player me) {
        super(context);
        this.me = me;
    }

    public void updateGames(List<Game> games) {
        List<Game> myTurnGames = new ArrayList<Game>();
        List<Game> oppTurnGames = new ArrayList<Game>();
        for (Game game : games) {
            if (imActiveUser(game)) {
                myTurnGames.add(game);
            } else {
                oppTurnGames.add(game);
            }
        }
        items.clear();
        addGames("MY TURN", myTurnGames);
        addGames("OPPONENT'S TURN", oppTurnGames);
        notifyDataSetChanged();
    }

    private void addGames(String sectionTitle, List<Game> games) {
        if (games.size() > 0) {
            items.add(new GamesSectionItemAdapter(sectionTitle));
            for (Game game : games) {
                items.add(new GameItemAdapter(game, me));
            }
        }
    }

    private boolean imActiveUser(Game game) {
        return me.equals(((KuridorGame) game).getActivePlayer());
    }
}
