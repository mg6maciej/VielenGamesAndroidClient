package com.elpassion.vielengames.ui;

import android.os.Bundle;

import com.elpassion.vielengames.R;
import com.elpassion.vielengames.data.kuridor.KuridorGameState;
import com.elpassion.vielengames.event.UpdatesEvent;

/**
 * Created by pawel on 16.06.14.
 */
public class GameActivity extends BaseActivity {

    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity);

        gameView = (GameView) findViewById(R.id.game_view);
        gameview.setGameState(KuridorGameState.initial());
    }

    public void onEvent(UpdatesEvent event) {
        
    }

}
