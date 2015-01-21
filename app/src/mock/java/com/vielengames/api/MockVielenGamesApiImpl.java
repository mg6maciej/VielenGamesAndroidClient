package com.vielengames.api;

import com.vielengames.data.Empty;
import com.vielengames.data.GameProposal;
import com.vielengames.data.Player;
import com.vielengames.data.SessionRequest;
import com.vielengames.data.SessionResponse;
import com.vielengames.data.Updates;
import com.vielengames.data.kuridor.KuridorMove;

import java.util.List;

import retrofit.Callback;

public final class MockVielenGamesApiImpl implements VielenGamesApi {

    @Override
    public void createSession(SessionRequest sessionRequest, Callback<SessionResponse> callback) {
        Player me = Player.builder()
                .id("id")
                .name("Me")
                .avatarUrl("https://raw.githubusercontent.com/assertgo/icon/master/assertgo_64.png")
                .build();
        SessionResponse response = SessionResponse.builder()
                .authToken("auth_token")
                .user(me)
                .build();
        callback.success(response, null);
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
