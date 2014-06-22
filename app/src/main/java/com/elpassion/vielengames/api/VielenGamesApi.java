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
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.http.Query;

public interface VielenGamesApi {

    @POST("/sessions")
    void createSession(@Body SessionRequest sessionRequest, Callback<SessionResponse> callback);

    @GET("/sessions/updates")
    void getSessionUpdates(@Query("since") String since, Callback<Updates> callback);

    @GET("/game_proposals")
    void getGameProposals(Callback<List<GameProposal>> callback);

    @POST("/game_proposals")
    void createGameProposal(@Body GameProposal proposal, Callback<GameProposal> callback);

    @PUT("/game_proposals/{proposal_id}/awaiting_players")
    void joinGameProposal(@Path("proposal_id") String proposalId, @Body Player player, Callback<GameProposal> callback);

    @DELETE("/game_proposals/{proposal_id}/awaiting_players/{player_id}")
    void leaveGameProposal(@Path("proposal_id") String proposalId, @Path("player_id") String playerId, Callback<Empty> callback);

    @POST("/games/{game_id}/moves")
    void move(@Path("game_id") String gameId, @Body KuridorMove move, Callback<Empty> callback);
}
