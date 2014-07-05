package com.elpassion.vielengames.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.elpassion.vielengames.R;
import com.elpassion.vielengames.data.kuridor.KuridorGame;
import com.elpassion.vielengames.utils.VanGogh;
import com.elpassion.vielengames.utils.ViewUtils;
import com.squareup.picasso.Picasso;

public final class ResultOverlayActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_overlay_activity);

        KuridorGame game = getIntent().getParcelableExtra("game");
        updateWinnerInfo(game);
        updateLoserInfo(game);
        setFinishOnPressListener();
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
}
