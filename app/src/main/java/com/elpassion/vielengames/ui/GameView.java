package com.elpassion.vielengames.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by pawel on 16.06.14.
 */
public class GameView extends View {

    private static final int FIELD_COUNT = 9;
    private static final int BORDER = 5;

    private Paint paint = new Paint();


    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);

        paint.setColor(Color.BLACK);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int dim;
        int width = this.getWidth() / FIELD_COUNT;
        int height = this.getHeight() / FIELD_COUNT;
        dim = (width < height) ? width : height;

        for (int i = 0; i < FIELD_COUNT; ++i) {
            for (int j = 0; j < FIELD_COUNT; ++j) {
                canvas.drawRect(j * dim, i * dim, (j + 1) * dim, (i + 1) * dim, paint);
                paint.setColor(Color.WHITE);
                canvas.drawRect(j * dim + BORDER, i * dim + BORDER, (j + 1) * dim - BORDER, (i + 1) * dim - BORDER, paint);
                paint.setColor(Color.BLACK);
            }
        }
    }
}
