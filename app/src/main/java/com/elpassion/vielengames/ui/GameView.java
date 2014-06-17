package com.elpassion.vielengames.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.elpassion.vielengames.R;
import com.elpassion.vielengames.data.Player;
import com.elpassion.vielengames.data.kuridor.KuridorGame;
import com.elpassion.vielengames.data.kuridor.KuridorGameState;
import com.elpassion.vielengames.data.kuridor.PawnPosition;
import com.elpassion.vielengames.data.kuridor.PositionConverter;
import com.elpassion.vielengames.data.kuridor.WallPosition;

/**
 * Created by pawel on 16.06.14.
 */
public class GameView extends View {

    private static final int FIELD_COUNT = 9;
    private static final int DOT_RADIUS = 5;
    //    private static final int BORDER = 4;
    private static final int WALL_WIDTH = DOT_RADIUS * 2;
    private static final int MAX_WALL_WIDTH = 2;
    private static final int PAWN_RADIUS_GAP = 6;

    private int START_X = DOT_RADIUS * 5;
    private int START_Y = DOT_RADIUS * 5;

    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private int clickedI = 0;
    private int clickedJ = 0;

    private int fieldDim;
    private int pawnRadius;

    private KuridorGame game;
    private Point startPoint = null;
    private Point endPoint = null;
    private Player player;
    private GestureDetector scrollDetector = new GestureDetector(this.getContext(), new ScrollDetector());
    private MoveRequestListener listener;
    private KuridorGameState gameState;

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setGame(KuridorGame game) {
        this.game = game;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setGameState(KuridorGameState gameState) {
        this.gameState = gameState;
    }

    public void setMoveListener(MoveRequestListener listener) {
        this.listener = listener;
    }

    private Point getPointForWallDrawing(int absX, int absY) {
        clickedI = (int) ((absX + (float) fieldDim / 2) / fieldDim);
        clickedJ = (int) ((absY + (float) fieldDim / 2) / fieldDim);

        return new Point(clickedI, clickedJ);
    }

    private Point fieldNumForAbsolute(int absX, int absY) {
        clickedI = (absX / fieldDim);
        clickedJ = (absY / fieldDim);

        return new Point(clickedI, clickedJ);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = (this.getWidth() - START_X) / FIELD_COUNT;
        int height = (this.getHeight() - START_Y) / FIELD_COUNT;
        fieldDim = (width < height) ? width : height;
        pawnRadius = fieldDim / 2 - PAWN_RADIUS_GAP;


        drawBoard(canvas, fieldDim);
        drawPawns(canvas, fieldDim);
        drawWalls(canvas, fieldDim);

//        paint.setTextSize(25);
        if (startPoint != null && endPoint != null) {
//            canvas.drawText(String.format("start(%d, %d) - end(%d, %d)", startPoint.x, startPoint.y, endPoint.x, endPoint.y), 50, 50, paint);
            paint.setStrokeWidth(WALL_WIDTH);
            canvas.drawLine(START_X + startPoint.x * fieldDim, START_Y + startPoint.y * fieldDim, START_X + endPoint.x * fieldDim, START_Y + endPoint.y * fieldDim, paint);
        }
    }

    private void drawBoard(Canvas canvas, int dim) {
        for (int y = 0; y < FIELD_COUNT + 1; ++y) {
            for (int x = 0; x < FIELD_COUNT + 1; ++x) {
                paint.setColor(Color.BLACK);
                canvas.drawCircle(START_X + x * dim, START_Y + y * dim, DOT_RADIUS, paint);
            }
        }
    }

    private void drawPawns(Canvas canvas, int dim) {
        paint.setColor(Color.BLUE);
        if (gameState != null) {
            for (PawnPosition pos : gameState.getPawns()) {//game.getCurrentState().getPawns()) {
                int pawnX = PositionConverter.getX(pos.getPosition());
                int pawnY = PositionConverter.getY(pos.getPosition());

                paint.setColor(getResources().getColor(R.color.green_normal));
                canvas.drawCircle(START_X + pawnX * dim + dim / 2, START_Y + pawnY * dim + dim / 2, pawnRadius, paint);
//                canvas.drawRect(pawnX * dim + BORDER, pawnY * dim + BORDER, (pawnX + 1) * dim - BORDER, (pawnY + 1) * dim - BORDER, paint);
            }
        }
    }

    private void drawWalls(Canvas canvas, int dim) {
        paint.setColor(Color.BLACK);
        if (gameState != null) {
            for (WallPosition wall : gameState.getWalls()) {// game.getCurrentState().getWalls()) {
                int wallX = PositionConverter.getX(wall.getPosition());
                int wallY = PositionConverter.getY(wall.getPosition());

                PositionConverter.Orientation ornt = PositionConverter.getOrientation(wall.getPosition());
                float startX = (ornt == PositionConverter.Orientation.hor) ? (wallX - 2) * dim : wallX * dim;
                float startY = (ornt == PositionConverter.Orientation.hor) ? wallY * dim : (wallY - 2) * dim;
                float stopX = (ornt == PositionConverter.Orientation.hor) ? wallX * dim : startX;
                float stopY = (ornt == PositionConverter.Orientation.hor) ? startY : wallY * dim;

                //canvas.drawRect(startX, startY, stopX, stopY, paint);

                paint.setStrokeWidth(WALL_WIDTH);
                canvas.drawLine(START_X + startX, START_Y + startY, START_X + stopX, START_Y + stopY, paint);
            }
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float x = event.getX();
        float y = event.getY();

        // TODO - real game and game state
        //if (game.getActivePlayer().getId() == this.player.getId()) {
        scrollDetector.onTouchEvent(event);
        this.invalidate();
        //}

        if (event.getAction() == MotionEvent.ACTION_UP && startPoint != null && endPoint != null) {
            System.out.println(getMovePostition());
            //listener.onRequest(game, new KuridorMove(KuridorMove.MoveType.wall, getMovePostition()));

            gameState.getWalls().add(WallPosition.builder().position(getMovePostition()).build());

            startPoint = endPoint = null;
        }

        return true;
    }

    private String getMovePostition() {
        Point wallStartPoint;
        if (isVertical(startPoint, endPoint)) {
            wallStartPoint = (startPoint.y > endPoint.y) ? startPoint : endPoint;

            return PositionConverter.getPosition(PositionConverter.Orientation.ver, wallStartPoint.x, wallStartPoint.y - 1);
        }

        if (isHorizontal(startPoint, endPoint)) {
            wallStartPoint = (startPoint.x > endPoint.x) ? startPoint : endPoint;

            return PositionConverter.getPosition(PositionConverter.Orientation.hor, wallStartPoint.x, wallStartPoint.y - 1);
        }

        return null;
    }

    private boolean isVertical(Point startPoint, Point endPoint) {
        return startPoint.x == endPoint.x;
    }

    private boolean isHorizontal(Point startPoint, Point endPoint) {
        return startPoint.y == endPoint.y;
    }

    private class ScrollDetector implements GestureDetector.OnGestureListener {

        @Override
        public boolean onDown(MotionEvent motionEvent) {
            //currentWall = new Rect((int) motionEvent.getX(), (int) motionEvent.getY(), (int) motionEvent.getX(), (int) motionEvent.getY());
            int x = (int) motionEvent.getX(), y = (int) motionEvent.getY();

            startPoint = getPointForWallDrawing(x, y);
            return true;
        }

        @Override
        public void onShowPress(MotionEvent motionEvent) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent motionEvent) {
            int x = (int) motionEvent.getX();
            int y = (int) motionEvent.getY();

            Point p = fieldNumForAbsolute(x, y);

            System.out.println(PositionConverter.getPosition(PositionConverter.Orientation.none, p.x, p.y));
            // TODO - real game
            //listener.onRequest(game, new KuridorMove(KuridorMove.MoveType.pawn, PositionConverter.getPosition(PositionConverter.Orientation.none, p.x, p.y)));

            return true;
        }

        @Override
        public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float v, float v2) {
            int x = (int) motionEvent2.getX();
            int y = (int) motionEvent2.getY();

            endPoint = getPointForWallDrawing(x, y);
            if (!isValidWall(startPoint, endPoint)) {
                endPoint = null;
            } else {
                alighEndPoint(startPoint, endPoint);
            }
            return true;
        }

        private void alighEndPoint(Point startPoint, Point endPoint) {
            if (isHorizontal(startPoint, endPoint)) {
                if (endPoint.x < startPoint.x) {
                    endPoint.x = startPoint.x - MAX_WALL_WIDTH;
                } else {
                    endPoint.x = startPoint.x + MAX_WALL_WIDTH;
                }
                return;
            }

            if (isVertical(startPoint, endPoint)) {
                if (endPoint.y < startPoint.y) {
                    endPoint.y = startPoint.y - MAX_WALL_WIDTH;
                } else {
                    endPoint.y = startPoint.y + MAX_WALL_WIDTH;
                }
                return;
            }
        }

        private boolean isValidWall(Point startPoint, Point endPoint) {

            return (isVertical(startPoint, endPoint) && ((Math.abs(startPoint.y - endPoint.y) >= MAX_WALL_WIDTH - 1) && (Math.abs(startPoint.y - endPoint.y) <= MAX_WALL_WIDTH + 1)))
                    ||
                    (isHorizontal(startPoint, endPoint) && ((Math.abs(startPoint.x - endPoint.x) >= MAX_WALL_WIDTH - 1) && (Math.abs(startPoint.x - endPoint.x) <= MAX_WALL_WIDTH + 1)));
        }

        @Override
        public void onLongPress(MotionEvent motionEvent) {

        }

        @Override
        public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float v, float v2) {
            return false;
        }
    }
}