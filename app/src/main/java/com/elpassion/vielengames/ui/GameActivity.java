package com.elpassion.vielengames.ui;

import android.os.Bundle;
import android.widget.ImageView;

import com.elpassion.vielengames.ForegroundNotifier;
import com.elpassion.vielengames.R;
import com.elpassion.vielengames.SessionUpdatesHandler;
import com.elpassion.vielengames.VielenGamesPrefs;
import com.elpassion.vielengames.api.VielenGamesClient;
import com.elpassion.vielengames.data.Game;
import com.elpassion.vielengames.data.Player;
import com.elpassion.vielengames.data.VielenGamesModel;
import com.elpassion.vielengames.data.kuridor.KuridorGame;
import com.elpassion.vielengames.data.kuridor.KuridorGameState;
import com.elpassion.vielengames.data.kuridor.KuridorMove;
import com.elpassion.vielengames.utils.ViewUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

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
    ForegroundNotifier notifier;
    @Inject
    VielenGamesModel model;

    public static final String GAME_ID_EXTRA = "com.elpassion.vielengames.EXTRA_GAME_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity);

        String gameId = getIntent().getExtras().getString(GAME_ID_EXTRA);

        gameView = (GameView) findViewById(R.id.game_view);
        gameView.setPlayer(prefs.getMe());

        KuridorGame thisGame = model.getGameById(gameId);
        if (thisGame != null) {
            List<Player> players = thisGame.getPlayers();
            updateViews(thisGame, players.get(0), R.id.game_player_1_name, R.id.game_player_1_profile_icon, R.id.game_player_1_active);
            updateViews(thisGame, players.get(1), R.id.game_player_2_name, R.id.game_player_2_profile_icon, R.id.game_player_2_active);
            gameView.setGame(thisGame);
        }
    }

    private void updateViews(KuridorGame game, Player player, int nameViewId, int profileIconViewId, int activeViewId) {
        if (player != null) {
            ViewUtils.setText(player.getName(), this, nameViewId);
            ImageView iconView = ViewUtils.findView(this, profileIconViewId);
            Picasso.with(this).load(player.getAvatarUrl()).into(iconView);
            boolean active = player.equals(game.getActivePlayer());
            ViewUtils.setVisible(active, this, activeViewId);
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
