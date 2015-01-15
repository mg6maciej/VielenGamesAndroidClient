package com.vielengames.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.vielengames.ForegroundNotifier;
import com.vielengames.R;
import com.vielengames.VielenGamesPrefs;
import com.vielengames.api.VielenGamesClient;
import com.vielengames.data.Game;
import com.vielengames.data.Player;
import com.vielengames.data.Team;
import com.vielengames.data.VielenGamesModel;
import com.vielengames.data.kuridor.KuridorGame;
import com.vielengames.data.kuridor.KuridorGameState;
import com.vielengames.data.kuridor.KuridorMove;
import com.vielengames.event.GamesUpdatedEvent;
import com.vielengames.event.MoveFailureEvent;
import com.vielengames.event.bus.EventBus;
import com.vielengames.ui.kuridor.GameHeaderLayout;
import com.vielengames.ui.kuridor.GameView;
import com.vielengames.utils.ViewUtils;
import com.vielengames.utils.kuridor.KuridorGameStateDrawer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
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
    @Inject
    VielenGamesPrefs prefs;

    @InstanceState
    private KuridorGame game;

    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity);
        setupToolbar();
        eventBus.register(this);

        gameView = (GameView) findViewById(R.id.game_view);
        gameView.setMoveListener(new GameView.MoveListener() {
            @Override
            public void onMove(KuridorMove move) {
                if (!imActiveUser()) {
                    if (noActivePlayer()) {
                        Toast.makeText(GameActivity.this, "Game is already finished", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(GameActivity.this, "It's not your turn", Toast.LENGTH_SHORT).show();
                    }
                } else if (!game.getCurrentState().isMoveValid(move)) {
                    Toast.makeText(GameActivity.this, "Illegal move: " + move.getPosition(), Toast.LENGTH_SHORT).show();
                } else {
                    gameClient.move(game, move);
                }
            }
        });
        if (game == null) {
            game = getIntent().getParcelableExtra(EXTRA_GAME);
        }
        updateGameView(Collections.singletonList(game));
        showGameHelpFirstTime();
    }

    private void showGameHelpFirstTime() {
        if (!prefs.getHelpOverlayAlreadyShown()) {
            prefs.setHelpOverlayAlreadyShown(true);
            showGameHelp();
        }
    }

    private void showGameHelp() {
        Intent intent = new Intent(this, GameHelpOverlayActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.game, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        boolean visible = prefs.getDeveloperMode();
        menu.findItem(R.id.mail_game).setVisible(visible);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.game_help:
                showGameHelp();
                return true;
            case R.id.mail_game:
                mailGame();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void mailGame() {
        Intent intent = new Intent(Intent.ACTION_SEND_MULTIPLE);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Vielen Game between "
                + game.getPlayers().get(0).getName()
                + " and "
                + game.getPlayers().get(1).getName());
        String text = "";
        for (int i = 0; i < game.getMoves().size(); i += 2) {
            text += (i / 2) + 1 + ". ";
            text += game.getMoves().get(i).getPosition() + " ";
            if (i + 1 < game.getMoves().size()) {
                text += game.getMoves().get(i + 1).getPosition();
            }
            text += "\n";
        }
        text += "\nDownload Vielen Games for Android from: https://play.google.com/store/apps/details?id=com.vielengames";
        intent.putExtra(Intent.EXTRA_TEXT, text);
        ArrayList<Uri> uris = new ArrayList<Uri>();
        KuridorGameState state = KuridorGameState.initial();
        int number = 0;
        try {
            toFile(state, uris, number);
            for (KuridorMove move : game.getMoves()) {
                state = state.move(move);
                number++;
                toFile(state, uris, number);
            }
        } catch (Exception ex) {
            Log.e("tag", "error on " + number, ex);
        }
        intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris);
        startActivityForResult(intent, 0);
    }

    private void toFile(KuridorGameState state, ArrayList<Uri> uris, int number) throws IOException {
        Bitmap bitmap = draw(state);
        File file = new File(getFilesDir(), "position_" + number + ".png");
        Log.e("tag", "file: " + file);
        FileOutputStream stream = openFileOutput("position_" + number + ".png", Context.MODE_WORLD_READABLE);
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        stream.close();
        uris.add(Uri.fromFile(file));
    }

    private Bitmap draw(KuridorGameState state) {
        int bitmapSize = 400;
        Bitmap bitmap = Bitmap.createBitmap(bitmapSize, bitmapSize, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(0xFFFFFFFF);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        KuridorGameStateDrawer.Settings settings = new KuridorGameStateDrawer.Settings()
                .width(bitmapSize)
                .height(bitmapSize)
                .paint(paint)
                .padding(20.0f)
                .dotsRadius(2.0f)
                .lastLineDotsRadius(3.0f)
                .wallWidth(6.0f)
                .wallPadding(5.0f)
                .pawnPadding(5.0f)
                .team1Color(getResources().getColor(R.color.green_normal))
                .team2Color(getResources().getColor(R.color.blue_normal))
                .flip(false);
        KuridorGameStateDrawer.draw(state, canvas, settings);
        return bitmap;
    }

    @SuppressWarnings("unused")
    public void onEvent(MoveFailureEvent event) {
        switch (event.getReason()) {
            case ILLEGAL_MOVE:
                Toast.makeText(GameActivity.this, "Server says: Illegal move: " + event.getMove().getPosition(), Toast.LENGTH_SHORT).show();
                break;
            case NOT_YOUR_TURN:
                Toast.makeText(GameActivity.this, "Server says: It's not your turn", Toast.LENGTH_SHORT).show();
                break;
            case GAME_FINISHED:
                Toast.makeText(GameActivity.this, "Server says: Game is already finished", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private boolean imActiveUser() {
        return prefs.getMe().equals(game.getActivePlayer());
    }

    private boolean noActivePlayer() {
        return game.getActivePlayer() == null;
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
        updateGameView(event.getGames());
    }

    private void updateGameView(List<? extends Game> games) {
        if (!games.contains(game)) {
            return;
        }
        KuridorGame thisGame = model.getGameById(game.getId());
        if (thisGame != null) {
            game = thisGame;
        }
        List<Player> players = game.getPlayers();
        Player team1Player = Team.FIRST.equals(players.get(0).getTeam()) ? players.get(0) : players.get(1);
        Player team2Player = Team.SECOND.equals(players.get(0).getTeam()) ? players.get(0) : players.get(1);
        boolean flip = prefs.getMe().equals(team2Player);
        KuridorGameState state = game.getCurrentState();
        gameView.setState(state, game.getLastMoveStartPosition(), imActiveUser(), flip);
        GameHeaderLayout top = ViewUtils.findView(this, R.id.game_header_top);
        GameHeaderLayout bottom = ViewUtils.findView(this, R.id.game_header_bottom);
        (flip ? bottom : top).update(team2Player, state.getSecondTeamState().getWallsLeft());
        (flip ? top : bottom).update(team1Player, state.getFirstTeamState().getWallsLeft());

        if (game.getWinner() != null) {
            ResultOverlayActivity.intent(this)
                    .game(game)
                    .start();
        }
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
