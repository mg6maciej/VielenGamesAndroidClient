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

    public static final String TAG = GameProposalsFragment.class.getSimpleName();
    @Inject
    VielenGamesClient client;
    @Inject
    EventBus eventBus;
    @Inject
    VielenGamesPrefs prefs;

    private ListView listView;

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
        listView.setAdapter(new GameProposalsAdapter(getActivity(), proposals, prefs.getMe(), client));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        eventBus.unregister(this);
        listView = null;
    }
}
