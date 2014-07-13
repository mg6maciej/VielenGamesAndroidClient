package com.vielengames.api;

import android.util.Log;

import com.vielengames.VielenGamesPrefs;
import com.vielengames.data.Empty;
import com.vielengames.data.GameProposal;
import com.vielengames.data.MoveFailure;
import com.vielengames.data.SessionRequest;
import com.vielengames.data.SessionResponse;
import com.vielengames.data.Updates;
import com.vielengames.data.kuridor.KuridorGame;
import com.vielengames.data.kuridor.KuridorMove;
import com.vielengames.event.MoveFailureEvent;
import com.vielengames.event.ProposalsUpdateEvent;
import com.vielengames.event.SessionCreateFailedEvent;
import com.vielengames.event.SessionStartedResponseEvent;
import com.vielengames.event.SessionUpdatesFailedEvent;
import com.vielengames.event.SessionUpdatesResponseEvent;
import com.vielengames.event.bus.EventBus;

import java.util.Collections;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public final class VielenGamesClient {

    public static final String TAG = VielenGamesClient.class.getSimpleName();

    private final EventBus eventBus;
    private final VielenGamesApi api;
    private final VielenGamesPrefs prefs;

    public VielenGamesClient(EventBus eventBus, VielenGamesApi api, VielenGamesPrefs prefs) {
        this.eventBus = eventBus;
        this.api = api;
        this.prefs = prefs;
    }

    public void createSession(SessionRequest sessionRequest) {
        api.createSession(sessionRequest, new Callback<SessionResponse>() {
            @Override
            public void success(SessionResponse sessionResponse, Response response) {
                prefs.setToken(sessionResponse.getAuthToken());
                prefs.setMe(sessionResponse.getUser());
                eventBus.post(new SessionStartedResponseEvent(sessionResponse));
            }

            @Override
            public void failure(RetrofitError error) {
                eventBus.post(new SessionCreateFailedEvent());
            }
        });
    }

    public void requestUpdates(String since) {
        api.getSessionUpdates(since, new Callback<Updates>() {
            @Override
            public void success(Updates updates, Response response) {
                eventBus.post(new SessionUpdatesResponseEvent(updates));
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                eventBus.post(new SessionUpdatesFailedEvent());
            }
        });
    }

    public void requestGameProposals() {
        api.getGameProposals(new ProposalsCallback());
    }

    public void createGameProposal(String gameType) {
        GameProposal proposal = GameProposal.builder()
                .gameType(gameType)
                .awaitingPlayers(Collections.singletonList(prefs.getMe()))
                .build();
        api.createGameProposal(proposal, new ProposalsCallback());
    }

    public void joinGameProposal(GameProposal proposal) {
        api.joinGameProposal(proposal.getId(), prefs.getMe(), new ProposalsCallback());
    }

    public void leaveGameProposal(GameProposal proposal) {
        api.leaveGameProposal(proposal.getId(), prefs.getMe().getId(), new ProposalsCallback());
    }

    public void move(KuridorGame game, final KuridorMove move) {
        api.move(game.getId(), move, new Callback<Empty>() {
            @Override
            public void success(Empty empty, Response response) {
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                if (!retrofitError.isNetworkError()) {
                    MoveFailure moveFailure = (MoveFailure) retrofitError.getBodyAs(MoveFailure.class);
                    eventBus.post(new MoveFailureEvent(moveFailure.getError(), move));
                }
            }
        });
    }

    private abstract class DefaultCallback<T> implements Callback<T> {

        @Override
        public void failure(RetrofitError retrofitError) {
            Log.e("tag", "retrofit", retrofitError);
        }
    }

    private final class ProposalsCallback extends DefaultCallback<List<GameProposal>> {

        @Override
        public void success(List<GameProposal> proposals, Response response) {
            for (GameProposal proposal : proposals) {
                proposal.updateCreationTime();
            }
            eventBus.post(new ProposalsUpdateEvent(proposals));
        }
    }
}
