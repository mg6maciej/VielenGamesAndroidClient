package com.vielengames.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.vielengames.R;
import com.vielengames.VielenGamesPrefs;
import com.vielengames.data.Game;
import com.vielengames.data.VielenGamesModel;
import com.vielengames.event.GameClickEvent;
import com.vielengames.event.GamesUpdatedEvent;
import com.vielengames.event.bus.EventBus;
import com.vielengames.utils.ViewUtils;

import java.util.List;

import javax.inject.Inject;

public final class MyGamesFragment extends BaseFragment {

    @Inject
    EventBus eventBus;
    @Inject
    VielenGamesModel model;
    @Inject
    VielenGamesPrefs prefs;

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
        adapter = new GamesAdapter(getActivity(), prefs.getMe());
        updateAdapter(model.getMyGames());
        listView = ViewUtils.findView(view, R.id.my_games_list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (adapter.getItem(position) instanceof Game) {
                    Game game = (Game) adapter.getItem(position);
                    eventBus.post(new GameClickEvent(game));
                }
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
