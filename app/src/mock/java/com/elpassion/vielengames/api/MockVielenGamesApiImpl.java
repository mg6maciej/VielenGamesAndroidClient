package com.elpassion.vielengames.api;

import com.elpassion.vielengames.data.GameProposal;

import retrofit.Callback;
import retrofit.client.Response;

public final class MockVielenGamesApiImpl implements VielenGamesApi {

    @Override
    public void getGameProposals(Callback<Response> callback) {
    }

    @Override
    public void createGameProposal(GameProposal proposal, Callback<Response> callback) {
    }

    @Override
    public void cancelGameProposal(String proposalId, Callback<Response> callback) {
    }
}
