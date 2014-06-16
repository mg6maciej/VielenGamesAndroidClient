package com.elpassion.vielengames.api;

import com.elpassion.vielengames.data.GameProposal;
import com.elpassion.vielengames.data.Move;
import com.elpassion.vielengames.data.Player;
import com.elpassion.vielengames.data.SessionRequest;
import com.elpassion.vielengames.data.SessionResponse;
import com.elpassion.vielengames.data.Updates;
import com.squareup.okhttp.Call;

import java.util.List;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.Path;
import retrofit.http.Query;

public final class MockVielenGamesApiImpl implements VielenGamesApi {

    @Override
    public void createSession(SessionRequest sessionRequest, Callback<SessionResponse> callback) {
    }

    @Override
    public void getSessionUpdates(String since, Callback<Updates> callback) {
    }

    @Override
    public void getGameProposals(Callback<List<GameProposal>> callback) {
    }

    @Override
    public void createGameProposal(GameProposal proposal, Callback<GameProposal> callback) {
    }

    @Override
    public void joinGameProposal(String proposalId, Player player, Callback<GameProposal> callback) {
    }

    @Override
    public void leaveGameProposal(String proposalId, String playerId, Callback<Response> callback) {
    }

    @Override
    public void move(Move move, Callback<Response> callback) {
    }
}
