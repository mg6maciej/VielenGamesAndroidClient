package com.elpassion.vielengames.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.view.View;
import android.widget.ImageView;

import com.elpassion.vielengames.R;
import com.elpassion.vielengames.VielenGamesPrefs;
import com.elpassion.vielengames.data.Player;
import com.elpassion.vielengames.data.kuridor.KuridorGame;
import com.elpassion.vielengames.utils.VanGogh;
import com.elpassion.vielengames.utils.ViewUtils;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

public final class ResultOverlayActivity extends BaseActivity {

    @Inject
    VielenGamesPrefs prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_overlay_activity);

        KuridorGame game = getIntent().getParcelableExtra("game");
        updateWinnerInfo(game);
        updateLoserInfo(game);
        updateWonLostInfo(game);
        setFinishOnPressListener();
    }

    private void updateWonLostInfo(KuridorGame game) {
        Player me = prefs.getMe();
        int title;
        int subtitle;
        if (me.equals(game.getWinner())) {
            title = R.string.result_won_title;
            subtitle = R.string.result_won_subtitle;
        } else {
            title = R.string.result_lost_title;
            subtitle = R.string.result_lost_subtitle;
        }
        ViewUtils.setText(title, this, R.id.result_overlay_title);
        ViewUtils.setText(subtitle, this, R.id.result_overlay_subtitle);
    }

    private void updateWinnerInfo(KuridorGame game) {
        ImageView winnerImage = ViewUtils.findView(this, R.id.result_overlay_face_won);
        VanGogh.loadCirclifiedInto(this, game.getWinner().getAvatarUrl(), winnerImage, R.dimen.result_overlay_image_size);
    }

    private void updateLoserInfo(KuridorGame game) {
        ImageView loserImage = ViewUtils.findView(this, R.id.result_overlay_face_lost);
        VanGogh.loadCirclifiedInto(this, game.getLoser().getAvatarUrl(), loserImage, R.dimen.result_overlay_image_size);
    }

    private void setFinishOnPressListener() {
        ViewUtils.setOnClickListener(this, R.id.result_overlay_container, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public static IntentBuilder intent(Context context) {
        return new IntentBuilder(context);
    }

    public static final class IntentBuilder {

        private final Context context;
        private KuridorGame game;

        public IntentBuilder(Context context) {
            this.context = context;
        }

        public IntentBuilder game(KuridorGame game) {
            this.game = game;
            return this;
        }

        public void start() {
            Intent intent = new Intent(context, ResultOverlayActivity.class);
            intent.putExtra("game", game);
            context.startActivity(intent);
        }
    }
}
