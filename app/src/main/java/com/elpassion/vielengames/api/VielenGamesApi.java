package com.elpassion.vielengames.api;

import com.elpassion.vielengames.data.GameProposal;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

public interface VielenGamesApi {

    @GET("/game_proposals")
    void getGameProposals(Callback<Response> callback);

    @POST("/game_proposals")
    void createGameProposal(@Body GameProposal proposal, Callback<Response> callback);

    @DELETE("/game_proposals/{proposal_id}")
    void cancelGameProposal(@Path("proposal_id") String proposalId, Callback<Response> callback);
}
