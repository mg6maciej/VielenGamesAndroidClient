package com.elpassion.vielengames.api;

import android.util.Log;

import com.elpassion.vielengames.VielenGamesPrefs;
import com.elpassion.vielengames.data.Empty;
import com.elpassion.vielengames.data.GameProposal;
import com.elpassion.vielengames.data.SessionRequest;
import com.elpassion.vielengames.data.SessionResponse;
import com.elpassion.vielengames.data.kuridor.KuridorGame;
import com.elpassion.vielengames.data.kuridor.KuridorMove;
import com.elpassion.vielengames.event.CreateGameProposalEvent;
import com.elpassion.vielengames.event.GetGameProposalsResponseEvent;
import com.elpassion.vielengames.event.JoinGameProposalResponseEvent;
import com.elpassion.vielengames.event.LeaveGameProposalResponseEvent;
import com.elpassion.vielengames.event.SessionResponseEvent;
import com.elpassion.vielengames.event.bus.EventBus;

import java.util.Collections;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public final class VielenGamesClient {

    private final EventBus eventBus;
    private final VielenGamesApi api;
    private VielenGamesPrefs prefs;

    public VielenGamesClient(EventBus eventBus, VielenGamesApi api, VielenGamesPrefs prefs) {
        this.eventBus = eventBus;
        this.api = api;
        this.prefs = prefs;
    }

    public void createSession(SessionRequest sessionRequest) {
        api.createSession(sessionRequest, new DefaultCallback<SessionResponse>() {
            @Override
            public void success(SessionResponse sessionResponse, Response response) {
                prefs.setToken(sessionResponse.getAuthToken());
                prefs.setMe(sessionResponse.getUser());
                eventBus.post(new SessionResponseEvent(sessionResponse));
            }
        });
    }

    public void requestGameProposals() {
        api.getGameProposals(new DefaultCallback<List<GameProposal>>() {
            @Override
            public void success(List<GameProposal> gameProposals, Response response) {
                eventBus.post(new GetGameProposalsResponseEvent(gameProposals));
            }
        });
    }

    public void createGameProposal(String gameType) {
        GameProposal proposal = GameProposal.builder()
                .gameType(gameType)
                .awaitingPlayers(Collections.singletonList(prefs.getMe()))
                .build();
        api.createGameProposal(proposal, new DefaultCallback<GameProposal>() {
            @Override
            public void success(GameProposal proposal, Response response) {
                eventBus.post(new CreateGameProposalEvent(proposal));
            }
        });
    }

    public void joinGameProposal(GameProposal proposal) {
        api.joinGameProposal(proposal.getId(), prefs.getMe(), new DefaultCallback<GameProposal>() {
            @Override
            public void success(GameProposal proposal, Response response) {
                eventBus.post(new JoinGameProposalResponseEvent(proposal));
            }
        });
    }

    public void leaveGameProposal(final GameProposal proposal) {
        api.leaveGameProposal(proposal.getId(), prefs.getMe().getId(), new DefaultCallback<Empty>() {
            @Override
            public void success(Empty empty, Response response) {
                eventBus.post(new LeaveGameProposalResponseEvent(proposal));
            }
        });
    }

    public void move(KuridorGame game, KuridorMove move) {
        api.move(game.getId(), move, new DefaultCallback<Empty>() {
            @Override
            public void success(Empty empty, Response response) {
            }
        });
    }

    private abstract class DefaultCallback<T> implements Callback<T> {

        @Override
        public void failure(RetrofitError retrofitError) {
            Log.e("tag", "retrofit", retrofitError);
        }
    }
}
