package com.vielengames.ui.kuridor;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.vielengames.R;
import com.vielengames.data.kuridor.KuridorGameState;
import com.vielengames.data.kuridor.KuridorMove;
import com.vielengames.ui.BaseView;
import com.vielengames.utils.kuridor.KuridorGameStateDrawer;

import lombok.Setter;

public final class GameView extends BaseView {


    public interface MoveListener {

        void onMove(KuridorMove move);
    }

    @Setter
    private MoveListener moveListener;

    private KuridorGameState state;
    private boolean flip;

    private KuridorGameStateDrawer.Settings drawerSettings;

    private GestureDetector gestureDetector;

    private KuridorMove currentWallMove;

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
                float x = getX(e);
                float y = getY(e);
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
                moveListener.onMove(KuridorMove.pawn("" + (char) ('a' + point.x) + (char) ('9' - point.y)));
                return true;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                currentWallMove = getWallMove(e1, e2);
                invalidate();
                return true;
            }
        });
    }

    private KuridorMove getWallMove(MotionEvent e1, MotionEvent e2) {
        float gameViewPadding = getResources().getDimension(R.dimen.game_view_padding);
        Point startPoint = toPoint(e1, gameViewPadding);
        Point endPoint = toPoint(e2, gameViewPadding);
        int diffX = Math.abs(endPoint.x - startPoint.x);
        int diffY = Math.abs(endPoint.y - startPoint.y);
        String move = null;
        if (isHorizontalWall(diffX, diffY)) {
            int centerX;
            int centerY = startPoint.y;
            if (endPoint.x > startPoint.x) {
                centerX = startPoint.x + 1;
            } else {
                centerX = startPoint.x - 1;
            }
            move = "" + (char) ('a' + centerX) + (char) ('9' - centerY) + "h";
        } else if (isVerticalWall(diffX, diffY)) {
            int centerX = startPoint.x;
            int centerY;
            if (endPoint.y > startPoint.y) {
                centerY = startPoint.y + 1;
            } else {
                centerY = startPoint.y - 1;
            }
            move = "" + (char) ('a' + centerX) + (char) ('9' - centerY) + "v";
        }
        if (move != null) {
            KuridorMove kuridorMove = KuridorMove.wall(move);
            if (state.isMoveValid(kuridorMove)) {
                return kuridorMove;
            }
        }
        return null;
    }

    private boolean isHorizontalWall(int diffX, int diffY) {
        return diffX >= 2 && diffX <= 3 && diffY <= 1;
    }

    private boolean isVerticalWall(int diffX, int diffY) {
        return diffY >= 2 && diffY <= 3 && diffX <= 1;
    }

    private Point toPoint(MotionEvent event, float gameViewPadding) {
        int smaller = Math.min(getWidth(), getHeight());
        float xPadding = gameViewPadding + (getWidth() - smaller) / 2.0f;
        float yPadding = gameViewPadding + (getHeight() - smaller) / 2.0f;
        float x = getX(event) - xPadding - (smaller - 1 - 2 * gameViewPadding) / 18.0f;
        float y = getY(event) - yPadding + (smaller - 1 - 2 * gameViewPadding) / 18.0f;
        Point point = new Point(
                (int) Math.floor(x / ((smaller - 1 - 2 * gameViewPadding) / 9.0f)),
                (int) Math.floor(y / ((smaller - 1 - 2 * gameViewPadding) / 9.0f)));
        return point;
    }

    private float getX(MotionEvent event) {
        if (flip) {
            return getWidth() - event.getX();
        } else {
            return event.getX();
        }
    }

    private float getY(MotionEvent event) {
        if (flip) {
            return getHeight() - event.getY();
        } else {
            return event.getY();
        }
    }

    public void setState(KuridorGameState state, boolean myTurn, boolean flip) {
        this.state = state;
        this.flip = flip;
        this.drawerSettings.drawLegalPawnMoves(myTurn);
        this.drawerSettings.flip(flip);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawerSettings
                .width(getWidth())
                .height(getHeight());
        KuridorGameStateDrawer.draw(state, canvas, drawerSettings);
        drawCurrentWallMove(canvas);
    }

    private void drawCurrentWallMove(Canvas canvas) {
        if (currentWallMove == null) {
            return;
        }
        int centerX = currentWallMove.getPosition().charAt(0) - 'a' + 1;
        int centerY = '8' - currentWallMove.getPosition().charAt(1) + 1;
        if (flip) {
            centerX = 9 - centerX;
            centerY = 9 - centerY;
        }
        int startX, startY, stopX, stopY;
        float wallPaddingX = 0.0f, wallPaddingY = 0.0f;
        if (currentWallMove.getPosition().charAt(2) == 'h') {
            startX = centerX - 1;
            startY = centerY;
            stopX = centerX + 1;
            stopY = centerY;
            wallPaddingX = drawerSettings.wallPadding();
        } else {
            startX = centerX;
            startY = centerY - 1;
            stopX = centerX;
            stopY = centerY + 1;
            wallPaddingY = drawerSettings.wallPadding();
        }
        int wallColor;
        if ("team_1".equals(state.getActiveTeam())) {
            wallColor = R.color.green_normal;
        } else {
            wallColor = R.color.blue_normal;
        }
        drawerSettings.paint().setColor(getContext().getResources().getColor(wallColor));
        drawerSettings.paint().setStrokeWidth(drawerSettings.wallWidth());
        int size = Math.min(drawerSettings.width(), drawerSettings.height());
        float xPadding = drawerSettings.padding() + (drawerSettings.width() - size) / 2.0f;
        float yPadding = drawerSettings.padding() + (drawerSettings.height() - size) / 2.0f;
        canvas.drawLine(
                xPadding + (size - 1 - 2 * drawerSettings.padding()) * startX / 9.0f + wallPaddingX,
                yPadding + (size - 1 - 2 * drawerSettings.padding()) * startY / 9.0f + wallPaddingY,
                xPadding + (size - 1 - 2 * drawerSettings.padding()) * stopX / 9.0f - wallPaddingX,
                yPadding + (size - 1 - 2 * drawerSettings.padding()) * stopY / 9.0f - wallPaddingY,
                drawerSettings.paint());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        if (event.getActionMasked() == MotionEvent.ACTION_UP) {
            if (currentWallMove != null) {
                moveListener.onMove(currentWallMove);
                currentWallMove = null;
                invalidate();
            }
        }
        return true;
    }
}
