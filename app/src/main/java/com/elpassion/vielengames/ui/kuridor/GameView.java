package com.elpassion.vielengames.ui.kuridor;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

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

    private GestureDetector gestureDetector;

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
        final float gameViewPadding = r.getDimension(R.dimen.game_view_padding);
        drawerSettings = new KuridorGameStateDrawer.Settings()
                .paint(paint)
                .padding(gameViewPadding)
                .dotsRadius(r.getDimension(R.dimen.game_view_dots_radius))
                .lastLineDotsRadius(r.getDimension(R.dimen.game_view_last_line_dots_radius))
                .wallWidth(r.getDimension(R.dimen.game_view_wall_width))
                .wallPadding(r.getDimension(R.dimen.game_view_wall_padding))
                .pawnPadding(r.getDimension(R.dimen.game_view_pawn_padding))
                .team1Color(r.getColor(R.color.green_normal))
                .team2Color(r.getColor(R.color.blue_normal));

        gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {

            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                int smaller = Math.min(getWidth(), getHeight());
                float xPadding = gameViewPadding + (getWidth() - smaller) / 2.0f;
                float yPadding = gameViewPadding + (getHeight() - smaller) / 2.0f;
                float x = e.getX();
                float y = e.getY();
                if (x < xPadding || y < yPadding
                        || x >= xPadding + smaller - 1 - 2 * gameViewPadding
                        || y >= yPadding + smaller - 1 - 2 * gameViewPadding) {
                    return true;
                }
                x -= xPadding;
                y -= yPadding;
                Point point = new Point(
                        (int) (x / ((smaller - 1 - 2 * gameViewPadding) / 9.0f)),
                        (int) (y / ((smaller - 1 - 2 * gameViewPadding) / 9.0f))
                );
                Log.e("move", "" + (char) ('a' + point.x) + (char) ('9' - point.y));
                moveListener.onMove(KuridorMove.pawn("" + (char) ('a' + point.x) + (char) ('9' - point.y)));
                return true;
            }
        });
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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }
}
