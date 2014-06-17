package com.elpassion.vielengames.ui;

import android.os.Bundle;
import android.widget.ImageView;

import com.elpassion.vielengames.R;
import com.elpassion.vielengames.SessionHandler;
import com.elpassion.vielengames.VielenGamesPrefs;
import com.elpassion.vielengames.api.VielenGamesClient;
import com.elpassion.vielengames.data.Game;
import com.elpassion.vielengames.data.kuridor.KuridorGame;
import com.elpassion.vielengames.data.kuridor.KuridorGameState;
import com.elpassion.vielengames.data.kuridor.KuridorMove;
import com.elpassion.vielengames.event.UpdatesEvent;
import com.elpassion.vielengames.utils.ViewUtils;
import com.squareup.picasso.Picasso;

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

        KuridorGame thisGame = null;
        for (Game game : sessionHandler.getGames()) {
            if (game.getId().equals(gameId)) {
                thisGame = (KuridorGame) game;
                gameView.setGame(thisGame);
                break;
            }
        }
        ViewUtils.setText(thisGame.getPlayers().get(0).getName(), this, R.id.game_player_1_name);
        ViewUtils.setText(thisGame.getPlayers().get(1).getName(), this, R.id.game_player_2_name);
        ImageView player1ProfileIcon = ViewUtils.findView(this, R.id.game_player_1_profile_icon);
        ImageView player2ProfileIcon = ViewUtils.findView(this, R.id.game_player_2_profile_icon);
        Picasso.with(this).load(thisGame.getPlayers().get(0).getAvatarUrl()).into(player1ProfileIcon);
        Picasso.with(this).load(thisGame.getPlayers().get(1).getAvatarUrl()).into(player2ProfileIcon);
    }

    @Override
    public void onRequest(KuridorGame game, KuridorMove move) {
        gameClient.move(game, move);
    }
}
