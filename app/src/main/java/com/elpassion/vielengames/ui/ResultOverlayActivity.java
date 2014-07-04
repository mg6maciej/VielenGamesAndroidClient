package com.elpassion.vielengames.ui;

import android.os.Bundle;
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
        ImageView winnerImage = ViewUtils.findView(this, R.id.result_overlay_face_won);
        VanGogh.loadCirclifiedInto(this, game.getPlayers().get(0).getAvatarUrl(), winnerImage, R.dimen.result_overlay_image_size);
        ImageView loserImage = ViewUtils.findView(this, R.id.result_overlay_face_lost);
        VanGogh.loadCirclifiedInto(this, game.getPlayers().get(1).getAvatarUrl(), loserImage, R.dimen.result_overlay_image_size);
    }
}
