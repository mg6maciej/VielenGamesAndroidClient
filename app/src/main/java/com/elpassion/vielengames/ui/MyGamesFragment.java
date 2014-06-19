package com.elpassion.vielengames.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.elpassion.vielengames.R;
import com.elpassion.vielengames.data.Game;
import com.elpassion.vielengames.data.VielenGamesModel;
import com.elpassion.vielengames.event.GameClickEvent;
import com.elpassion.vielengames.event.GamesUpdatedEvent;
import com.elpassion.vielengames.event.bus.EventBus;
import com.elpassion.vielengames.utils.ViewUtils;

import java.util.List;

import javax.inject.Inject;

public final class MyGamesFragment extends BaseFragment {

    @Inject
    EventBus eventBus;
    @Inject
    VielenGamesModel model;

    private GamesAdapter adapter;
    private ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.my_games_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        eventBus.register(this);
        adapter = new GamesAdapter(getActivity());
        updateAdapter(model.getMyGames());
        listView = ViewUtils.findView(view, R.id.my_games_list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Game game = adapter.getItem(position);
                eventBus.post(new GameClickEvent(game));
            }
        });
    }

    @SuppressWarnings("unused")
    public void onEvent(GamesUpdatedEvent event) {
        updateAdapter(model.getMyGames());
    }

    private void updateAdapter(List<Game> games) {
        adapter.updateGames(games);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        eventBus.unregister(this);
        adapter = null;
        listView = null;
    }
}
