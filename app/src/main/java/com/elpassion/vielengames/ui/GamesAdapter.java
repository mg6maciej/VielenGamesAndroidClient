package com.elpassion.vielengames.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.elpassion.vielengames.R;
import com.elpassion.vielengames.data.Game;
import com.elpassion.vielengames.data.Player;
import com.elpassion.vielengames.data.kuridor.KuridorGame;
import com.elpassion.vielengames.utils.ViewUtils;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public final class GamesAdapter extends BaseAdapter {

    private final LayoutInflater inflater;
    private final Set<Game> gameSet;
    private List<Game> gameList;

    public GamesAdapter(Context context) {
        this.inflater = LayoutInflater.from(context);
        this.gameSet = new LinkedHashSet<Game>();
        this.gameList = new ArrayList<Game>();
    }

    @Override
    public int getCount() {
        return gameSet.size();
    }

    @Override
    public Game getItem(int position) {
        return gameList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.game_item, parent, false);
        }
        KuridorGame item = (KuridorGame) getItem(position);
        Player player1 = item.getPlayers().get(0);
        Player player2 = item.getPlayers().get(1);
        ViewUtils.setText(player1.getName(), convertView, R.id.game_item_player_1_name);
        ViewUtils.setText(player2.getName(), convertView, R.id.game_item_player_2_name);
        return convertView;
    }

    public void updateGames(List<Game> games) {
        this.gameSet.addAll(games);
        this.gameList = new ArrayList<Game>(gameSet);
        notifyDataSetChanged();
    }
}
