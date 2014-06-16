package com.elpassion.vielengames.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.elpassion.vielengames.data.kuridor.KuridorGameState;
import com.elpassion.vielengames.data.kuridor.PawnPosition;
import com.elpassion.vielengames.data.kuridor.PositionConverter;

/**
 * Created by pawel on 16.06.14.
 */
public class GameView extends View {

    private static final int FIELD_COUNT = 9;
    private static final int BORDER = 5;

    private Paint paint = new Paint();
    private KuridorGameState gameState;


    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);

        paint.setColor(Color.BLACK);
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

        for (int y = 0; y < FIELD_COUNT; ++y) {
            for (int x = 0; x < FIELD_COUNT; ++x) {
                canvas.drawRect(x * dim, y * dim, (x + 1) * dim, (y + 1) * dim, paint);

                paint.setColor(Color.WHITE);
                for (PawnPosition pos : gameState.getPawns()) {
                    int pawnX = PositionConverter.getX(pos.getPosition());
                    int pawnY = PositionConverter.getY(pos.getPosition());

                    if (pawnX == x && pawnY == y) {
                        paint.setColor(Color.BLUE);
                    }
                }
                canvas.drawRect(x * dim + BORDER, y * dim + BORDER, (x + 1) * dim - BORDER, (y + 1) * dim - BORDER, paint);
                paint.setColor(Color.BLACK);
            }
        }
    }
}
