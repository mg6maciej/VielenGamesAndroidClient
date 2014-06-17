package com.elpassion.vielengames.ui;

import android.os.Bundle;

import com.elpassion.vielengames.R;
import com.elpassion.vielengames.SessionHandler;
import com.elpassion.vielengames.VielenGamesPrefs;
import com.elpassion.vielengames.api.VielenGamesClient;
import com.elpassion.vielengames.data.Game;
import com.elpassion.vielengames.data.kuridor.KuridorGame;
import com.elpassion.vielengames.data.kuridor.KuridorGameState;
import com.elpassion.vielengames.data.kuridor.KuridorMove;
import com.elpassion.vielengames.event.UpdatesEvent;

import javax.inject.Inject;

/**
 * Created by pawel on 16.06.14.
 */
public class GameActivity extends BaseActivity implements MoveRequestListener {

    private GameView gameView;

    @Inject
    VielenGamesClient gameClient;

    @Inject
    VielenGamesPrefs prefs;

    @Inject
    SessionHandler sessionHandler;

    public static final String GAME_ID_EXTRA = "com.elpassion.vielengames.EXTRA_GAME_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity);

        String gameId = getIntent().getExtras().getString(GAME_ID_EXTRA);

        gameView = (GameView) findViewById(R.id.game_view);
        gameView.setPlayer(prefs.getMe());
        gameView.setGameState(KuridorGameState.initial());

        for (Game game : sessionHandler.getGames()) {
            if (game.getId().equals(gameId)) {
                gameView.setGame((KuridorGame) game);
                break;
            }
        }
    }

    @Override
    public void onRequest(KuridorGame game, KuridorMove move) {
        gameClient.move(game, move);
    }
}
