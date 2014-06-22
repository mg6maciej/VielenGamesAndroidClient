package com.elpassion.vielengames.ui.kuridor;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;

import com.elpassion.vielengames.R;
import com.elpassion.vielengames.data.kuridor.KuridorGameState;
import com.elpassion.vielengames.data.kuridor.KuridorMove;
import com.elpassion.vielengames.ui.BaseView;
import com.elpassion.vielengames.utils.kuridor.KuridorGameStateDrawer;

import lombok.Setter;

public final class GameView extends BaseView {


    public interface MoveListener {

        void onMove(KuridorMove move);
    }

    @Setter
    private MoveListener moveListener;

    private KuridorGameState state;

    private KuridorGameStateDrawer.Settings drawerSettings;

    @SuppressWarnings("unused")
    public GameView(Context context) {
        super(context);
        init();
    }

    @SuppressWarnings("unused")
    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @SuppressWarnings("unused")
    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        Resources r = getResources();
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        drawerSettings = new KuridorGameStateDrawer.Settings()
                .paint(paint)
                .padding(r.getDimension(R.dimen.game_view_padding))
                .dotsRadius(r.getDimension(R.dimen.game_view_dots_radius))
                .lastLineDotsRadius(r.getDimension(R.dimen.game_view_last_line_dots_radius))
                .wallWidth(r.getDimension(R.dimen.game_view_wall_width))
                .wallPadding(r.getDimension(R.dimen.game_view_wall_padding))
                .pawnPadding(r.getDimension(R.dimen.game_view_pawn_padding))
                .team1Color(r.getColor(R.color.green_normal))
                .team2Color(r.getColor(R.color.blue_normal));
    }

    public void setState(KuridorGameState state) {
        this.state = state;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawerSettings
                .width(getWidth())
                .height(getHeight());
        KuridorGameStateDrawer.draw(state, canvas, drawerSettings);
    }
}
