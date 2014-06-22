package com.elpassion.vielengames.ui;

import android.content.Context;
import android.content.Intent;
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
import com.elpassion.vielengames.ui.kuridor.GameView;
import com.elpassion.vielengames.utils.ViewUtils;

import java.util.List;

import javax.inject.Inject;

import hrisey.InstanceState;

public class GameActivity extends BaseActivity {

    private static final String EXTRA_GAME = "game";

    @Inject
    VielenGamesClient gameClient;
    @Inject
    EventBus eventBus;
    @Inject
    ForegroundNotifier notifier;
    @Inject
    VielenGamesModel model;

    @InstanceState
    private KuridorGame game;

    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity);
        eventBus.register(this);

        gameView = (GameView) findViewById(R.id.game_view);
        gameView.setMoveListener(new GameView.MoveListener() {
            @Override
            public void onMove(KuridorMove move) {
                gameClient.move(game, move);
            }
        });
        if (game == null) {
            game = getIntent().getParcelableExtra(EXTRA_GAME);
        }
        updateGameView();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(EXTRA_GAME, game);
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
        KuridorGame thisGame = model.getGameById(game.getId());
        if (thisGame != null) {
            game = thisGame;
        }
        KuridorGameState state = game.getCurrentState();
        gameView.setState(state);
        List<Player> players = game.getPlayers();
        Player team1Player = "team_1".equals(players.get(0).getTeam()) ? players.get(0) : players.get(1);
        Player team2Player = "team_2".equals(players.get(0).getTeam()) ? players.get(0) : players.get(1);
        GameHeaderLayout top = ViewUtils.findView(this, R.id.game_header_top);
        top.update(team2Player, state.getTeam2().getWallsLeft(), "team_2".equals(state.getActiveTeam()));
        GameHeaderLayout bottom = ViewUtils.findView(this, R.id.game_header_bottom);
        bottom.update(team1Player, state.getTeam1().getWallsLeft(), "team_1".equals(state.getActiveTeam()));
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

    public static IntentBuilder intent(Context context) {
        return new IntentBuilder(context);
    }

    public static class IntentBuilder {

        private final Context context;
        private KuridorGame game;

        IntentBuilder(Context context) {
            this.context = context;
        }

        public IntentBuilder game(KuridorGame game) {
            this.game = game;
            return this;
        }

        public void start() {
            Intent intent = new Intent(context, GameActivity.class);
            intent.putExtra(EXTRA_GAME, game);
            context.startActivity(intent);
        }
    }
}
