package com.elpassion.vielengames.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.elpassion.vielengames.R;
import com.elpassion.vielengames.VielenGamesPrefs;
import com.elpassion.vielengames.api.VielenGamesClient;
import com.elpassion.vielengames.data.GameProposal;
import com.elpassion.vielengames.event.GetGameProposalsResponseEvent;
import com.elpassion.vielengames.event.bus.EventBus;
import com.elpassion.vielengames.utils.ViewUtils;

import java.util.ArrayList;

import javax.inject.Inject;

import hrisey.InstanceState;

public final class GameProposalsFragment extends BaseFragment {

    @Inject
    VielenGamesClient client;
    @Inject
    EventBus eventBus;
    @Inject
    VielenGamesPrefs prefs;

    private ListView listView;
    private GameProposalsAdapter adapter;
    private Runnable refreshAdapter = createRefreshAdapterAction();

    @InstanceState
    private ArrayList<GameProposal> proposals;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.game_proposals_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        eventBus.register(this);
        listView = ViewUtils.findView(view, R.id.game_proposals_list);
        listView.setEmptyView(ViewUtils.findView(view, R.id.game_proposals_loading_indicator));
        if (proposals == null) {
            client.requestGameProposals();
        } else {
            updateListView();
        }
    }

    @SuppressWarnings("unused")
    public void onEvent(GetGameProposalsResponseEvent event) {
        proposals = new ArrayList<GameProposal>(event.getGameProposals());
        updateListView();
    }

    private void updateListView() {
        adapter = new GameProposalsAdapter(getActivity(), proposals, prefs.getMe(), client);
        listView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshAdapter.run();
    }

    @Override
    public void onPause() {
        super.onPause();
        listView.removeCallbacks(refreshAdapter);
    }

    private Runnable createRefreshAdapterAction() {
        return new Runnable() {
            @Override
            public void run() {
                if (adapter != null) {
                    adapter.notifyDataSetChanged();
                }
                listView.postDelayed(this, 60 * 1000);
            }
        };
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        eventBus.unregister(this);
        listView = null;
    }
}
