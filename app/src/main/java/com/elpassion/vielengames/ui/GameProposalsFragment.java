package com.elpassion.vielengames.ui;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.elpassion.vielengames.R;
import com.elpassion.vielengames.VielenGamesPrefs;
import com.elpassion.vielengames.api.VielenGamesClient;
import com.elpassion.vielengames.data.GameProposal;
import com.elpassion.vielengames.event.CreateGameProposalEvent;
import com.elpassion.vielengames.event.GetGameProposalsResponseEvent;
import com.elpassion.vielengames.event.JoinGameProposalResponseEvent;
import com.elpassion.vielengames.event.LeaveGameProposalResponseEvent;
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

    private View root;
    private ListView listView;
    private SwipeRefreshLayout swipeRefreshLayout;
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
        root = view;
        listView = ViewUtils.findView(view, R.id.game_proposals_list);
        swipeRefreshLayout = ViewUtils.findView(view, R.id.game_proposals_swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestGameProposals();
            }
        });
        swipeRefreshLayout.setColorSchemeResources(
                android.R.color.black,
                R.color.green_normal,
                android.R.color.black,
                R.color.green_normal);
        ViewUtils.setOnClickListener(view, R.id.game_proposals_add_proposal_button, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                client.createGameProposal("kuridor");
            }
        });
        if (proposals == null) {
            requestGameProposals();
        } else {
            updateListView();
        }
    }

    @SuppressWarnings("unused")
    public void onEvent(GetGameProposalsResponseEvent event) {
        proposals = new ArrayList<GameProposal>(event.getGameProposals());
        updateListView();
    }

    @SuppressWarnings("unused")
    public void onEvent(LeaveGameProposalResponseEvent event) {
        requestGameProposals();
    }

    @SuppressWarnings("unused")
    public void onEvent(CreateGameProposalEvent event) {
        requestGameProposals();
    }

    @SuppressWarnings("unused")
    public void onEvent(JoinGameProposalResponseEvent event) {
        requestGameProposals();
    }

    public void requestGameProposals() {
        client.requestGameProposals();
        swipeRefreshLayout.setRefreshing(true);
    }

    private void updateListView() {
        ViewUtils.setVisible(proposals.size() == 0, root, R.id.game_proposals_no_game_proposals);
        adapter = new GameProposalsAdapter(getActivity(), proposals, prefs.getMe(), client);
        listView.setAdapter(adapter);
        swipeRefreshLayout.setRefreshing(false);
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
        root = null;
        listView = null;
        swipeRefreshLayout = null;
        adapter = null;
    }
}
