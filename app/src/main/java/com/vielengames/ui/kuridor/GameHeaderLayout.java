package com.vielengames.ui.kuridor;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.vielengames.R;
import com.vielengames.data.Player;
import com.vielengames.ui.BaseLinearLayout;
import com.vielengames.utils.VanGogh;
import com.vielengames.utils.ViewUtils;

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
        VanGogh.loadCirclifiedInto(getContext(), player.getAvatarUrl(), imageView, R.dimen.common_image_size);
        int drawableId = "team_1".equals(player.getTeam())
                ? R.drawable.green_circle
                : R.drawable.blue_circle;
        ViewUtils.setBackground(drawableId, this, R.id.game_header_player_color);
        ViewUtils.setText(player.getName(), this, R.id.game_header_player_name);
        ViewUtils.setText(String.valueOf(wallsLeft), this, R.id.game_header_walls_left);
        ViewUtils.setVisible(active, this, R.id.game_header_player_active);
    }
}
