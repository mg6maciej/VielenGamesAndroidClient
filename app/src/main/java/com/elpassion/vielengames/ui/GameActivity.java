package com.elpassion.vielengames.ui;

import android.os.Bundle;

import com.elpassion.vielengames.ForegroundNotifier;
import com.elpassion.vielengames.R;
import com.elpassion.vielengames.VielenGamesPrefs;
import com.elpassion.vielengames.api.VielenGamesClient;
import com.elpassion.vielengames.data.Player;
import com.elpassion.vielengames.data.VielenGamesModel;
import com.elpassion.vielengames.data.kuridor.KuridorGame;
import com.elpassion.vielengames.data.kuridor.KuridorGameState;
import com.elpassion.vielengames.data.kuridor.KuridorMove;
import com.elpassion.vielengames.event.GamesUpdatedEvent;
import com.elpassion.vielengames.event.bus.EventBus;
import com.elpassion.vielengames.ui.kuridor.GameHeaderLayout;
import com.elpassion.vielengames.utils.ViewUtils;

import java.util.List;

import javax.inject.Inject;

public class GameActivity extends BaseActivity implements MoveRequestListener {

    private GameView gameView;

    @Inject
    VielenGamesClient gameClient;
    @Inject
    EventBus eventBus;
    @Inject
    VielenGamesPrefs prefs;
    @Inject
    ForegroundNotifier notifier;
    @Inject
    VielenGamesModel model;

    public static final String GAME_ID_EXTRA = "com.elpassion.vielengames.EXTRA_GAME_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity);
        eventBus.register(this);

        gameView = (GameView) findViewById(R.id.game_view);
        gameView.setPlayer(prefs.getMe());
        gameView.setMoveListener(this);

        updateGameView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        eventBus.unregister(this);
    }

    @SuppressWarnings("unused")
    public void onEvent(GamesUpdatedEvent event) {
        updateGameView();
    }

    private void updateGameView() {
        String gameId = getIntent().getExtras().getString(GAME_ID_EXTRA);
        KuridorGame thisGame = model.getGameById(gameId);
        if (thisGame != null) {
            gameView.setGame(thisGame);
            List<Player> players = thisGame.getPlayers();
            KuridorGameState state = thisGame.getCurrentState();
            Player team1Player = "team_1".equals(players.get(0).getTeam()) ? players.get(0) : players.get(1);
            Player team2Player = "team_2".equals(players.get(0).getTeam()) ? players.get(0) : players.get(1);
            GameHeaderLayout top = ViewUtils.findView(this, R.id.game_header_top);
            top.update(team2Player, state.getTeam2().getWallsLeft(), "team_2".equals(state.getActiveTeam()));
            GameHeaderLayout bottom = ViewUtils.findView(this, R.id.game_header_bottom);
            bottom.update(team1Player, state.getTeam1().getWallsLeft(), "team_1".equals(state.getActiveTeam()));
        }
    }

    @Override
    public void onRequest(KuridorGame game, KuridorMove move) {
        gameClient.move(game, move);
    }

    @Override
    protected void onStart() {
        super.onStart();
        notifier.onActivityStarted();
    }

    @Override
    protected void onStop() {
        super.onStop();
        notifier.onActivityStopped();
    }
}
