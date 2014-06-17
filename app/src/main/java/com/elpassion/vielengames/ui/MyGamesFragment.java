package com.elpassion.vielengames.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.elpassion.vielengames.R;
import com.elpassion.vielengames.SessionHandler;
import com.elpassion.vielengames.api.VielenGamesClient;
import com.elpassion.vielengames.data.Game;
import com.elpassion.vielengames.data.Updates;
import com.elpassion.vielengames.event.GameClickEvent;
import com.elpassion.vielengames.event.GamesUpdatedEvent;
import com.elpassion.vielengames.event.UpdatesEvent;
import com.elpassion.vielengames.event.bus.EventBus;
import com.elpassion.vielengames.utils.ViewUtils;

import java.util.Set;

import javax.inject.Inject;

public final class MyGamesFragment extends BaseFragment {

    @Inject
    VielenGamesClient client;
    @Inject
    EventBus eventBus;
    @Inject
    SessionHandler handler;

    private ListView listView;
    private GamesAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.my_games_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        eventBus.register(this);
        listView = ViewUtils.findView(view, R.id.my_games_list);
        adapter = new GamesAdapter(getActivity());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Game game = adapter.getItem(position);
                eventBus.post(new GameClickEvent(game));
            }
        });
        updateAdapter(handler.getGames());
    }

    @SuppressWarnings("unused")
    public void onEvent(GamesUpdatedEvent event) {
        updateAdapter(event.getGames());
    }

    private void updateAdapter(Set<Game> games) {
        adapter.updateGames(games);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        eventBus.unregister(this);
        listView = null;
    }
}
