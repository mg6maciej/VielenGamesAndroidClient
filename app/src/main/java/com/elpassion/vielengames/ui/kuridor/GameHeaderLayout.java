package com.elpassion.vielengames.ui.kuridor;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.elpassion.vielengames.R;
import com.elpassion.vielengames.data.Player;
import com.elpassion.vielengames.ui.BaseLinearLayout;
import com.elpassion.vielengames.utils.ViewUtils;
import com.squareup.picasso.Picasso;

public final class GameHeaderLayout extends BaseLinearLayout {

    public GameHeaderLayout(Context context) {
        super(context);
        init();
    }

    public GameHeaderLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setOrientation(HORIZONTAL);
        inflate(getContext(), R.layout.game_header_layout, this);
    }

    public void update(Player player, int wallsLeft, boolean active) {
        ImageView imageView = ViewUtils.findView(this, R.id.game_header_player_profile_icon);
        Picasso.with(getContext()).load(player.getAvatarUrl()).into(imageView);
        ViewUtils.setText(player.getName(), this, R.id.game_header_player_name);
        ViewUtils.setText(String.valueOf(wallsLeft), this, R.id.game_header_walls_left);
        ViewUtils.setVisible(active, this, R.id.game_header_player_active);
    }
}
