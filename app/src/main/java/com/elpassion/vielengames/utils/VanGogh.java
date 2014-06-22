package com.elpassion.vielengames.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.elpassion.vielengames.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

public final class VanGogh {

    public static void loadCirclifiedInto(final Context context, String url, ImageView into) {
        Picasso.with(context)
                .load(url)
                .resizeDimen(R.dimen.common_image_size, R.dimen.common_image_size)
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
