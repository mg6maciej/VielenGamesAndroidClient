package com.elpassion.vielengames.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.elpassion.vielengames.data.kuridor.KuridorGameState;
import com.elpassion.vielengames.data.kuridor.PawnPosition;
import com.elpassion.vielengames.data.kuridor.PositionConverter;
import com.elpassion.vielengames.data.kuridor.WallPosition;

/**
 * Created by pawel on 16.06.14.
 */
public class GameView extends View {

    private static final int FIELD_COUNT = 9;
    private static final int BORDER = 5;
    private static final int WALL_WIDTH = 15;

    private Paint paint = new Paint();

    private KuridorGameState gameState;

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setGameState(KuridorGameState state) {
        gameState = state;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int dim;
        int width = this.getWidth() / FIELD_COUNT;
        int height = this.getHeight() / FIELD_COUNT;
        dim = (width < height) ? width : height;

        drawBoard(canvas, dim);

        drawPlayers(canvas, dim);
        
        drawWalls(canvas, dim);
    }

    private void drawBoard(Canvas canvas, int dim) {
        for (int y = 0; y < FIELD_COUNT; ++y) {
            for (int x = 0; x < FIELD_COUNT; ++x) {
                paint.setColor(Color.BLACK);
                canvas.drawRect(x * dim, y * dim, (x + 1) * dim, (y + 1) * dim, paint);

                paint.setColor(Color.WHITE);
                canvas.drawRect(x * dim + BORDER, y * dim + BORDER, (x + 1) * dim - BORDER, (y + 1) * dim - BORDER, paint);
            }
        }
    }

    private void drawPlayers(Canvas canvas, int dim) {
        paint.setColor(Color.BLUE);
        if (gameState != null) {
            for (PawnPosition pos : gameState.getPawns()) {
                int pawnX = PositionConverter.getX(pos.getPosition());
                int pawnY = PositionConverter.getY(pos.getPosition());

                canvas.drawRect(pawnX * dim + BORDER, pawnY * dim + BORDER, (pawnX + 1) * dim - BORDER, (pawnY + 1) * dim - BORDER, paint);
            }
        }
    }

    private void drawWalls(Canvas canvas, int dim) {
        paint.setColor(Color.RED);
        if (gameState != null) {
            for (WallPosition wall : gameState.getWalls()) {
                int wallX = PositionConverter.getX(wall.getPosition());
                int wallY = PositionConverter.getY(wall.getPosition());

                PositionConverter.Orientation ornt = PositionConverter.getOrientation(wall.getPosition());
                float startX = (ornt == PositionConverter.Orientation.hor) ? (wallX - 2) * dim : wallX * dim;
                float startY = (ornt == PositionConverter.Orientation.hor) ? wallY * dim : (wallY - 2) * dim;
                float stopX = (ornt == PositionConverter.Orientation.hor) ? wallX * dim : startX + WALL_WIDTH;
                float stopY = (ornt == PositionConverter.Orientation.hor) ? startY + WALL_WIDTH : wallY * dim;

                canvas.drawRect(startX, startY, stopX, stopY, paint);
            }
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float x = event.getX();
        float y = event.getY();


        return true;
    }
}
