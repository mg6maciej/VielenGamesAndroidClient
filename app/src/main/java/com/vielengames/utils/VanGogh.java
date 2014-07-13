package com.vielengames.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.DimenRes;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import com.vielengames.R;

public final class VanGogh {

    public static void loadCirclifiedInto(final Context context, String url, ImageView into, @DimenRes int imageSize) {
        Picasso.with(context)
                .load(url)
                .resizeDimen(imageSize, imageSize)
                .centerCrop()
                .transform(new Transformation() {
                    @Override
                    public Bitmap transform(Bitmap bitmap) {
                        final int circleColor = 0xFFEEEEEE;
                        final float circleWidth = context.getResources().getDimension(R.dimen.common_circle_width);
                        Bitmap out = Circlifier.circlify(bitmap, circleColor, circleWidth);
                        bitmap.recycle();
                        return out;
                    }

                    @Override
                    public String key() {
                        return "circlifier";
                    }
                })
                .into(into);
    }
}
