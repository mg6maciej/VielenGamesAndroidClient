package com.elpassion.vielengames.api;

import com.elpassion.vielengames.data.Empty;
import com.elpassion.vielengames.data.GameProposal;
import com.elpassion.vielengames.data.Player;
import com.elpassion.vielengames.data.SessionRequest;
import com.elpassion.vielengames.data.SessionResponse;
import com.elpassion.vielengames.data.Updates;
import com.elpassion.vielengames.data.kuridor.KuridorMove;

import java.util.List;

import retrofit.Callback;

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
    public void createGameProposal(GameProposal proposal, Callback<List<GameProposal>> callback) {
    }

    @Override
    public void joinGameProposal(String proposalId, Player player, Callback<List<GameProposal>> callback) {
    }

    @Override
    public void leaveGameProposal(String proposalId, String playerId, Callback<List<GameProposal>> callback) {
    }

    @Override
    public void move(String gameId, KuridorMove move, Callback<Empty> callback) {
    }
}
