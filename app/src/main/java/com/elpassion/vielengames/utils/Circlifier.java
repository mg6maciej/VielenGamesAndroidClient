package com.elpassion.vielengames.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;

public final class Circlifier {

    private Circlifier() {
    }

    public static Bitmap circlify(final Bitmap bitmap, final int circleColor, final float circleWidth) {
        final int bitmapSize = Math.min(bitmap.getWidth(), bitmap.getHeight());
        final float center = (bitmapSize - 1) / 2.0f;
        final float radius = center - circleWidth / 2;
        final Bitmap out = Bitmap.createBitmap(bitmapSize, bitmapSize, Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(out);
        final BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(shader);
        canvas.drawCircle(center, center, radius, paint);
        paint.setShader(null);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(circleColor);
        paint.setStrokeWidth(circleWidth);
        canvas.drawCircle(center, center, radius, paint);
        return out;
    }
}
