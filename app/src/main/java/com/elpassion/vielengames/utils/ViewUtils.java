package com.elpassion.vielengames.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public final class ViewUtils {

    private ViewUtils() {
    }

    public static <T extends View> T findView(View parent, int viewId) {
        return (T) parent.findViewById(viewId);
    }

    public static <T extends View> T findView(Activity activity, int viewId) {
        return (T) activity.findViewById(viewId);
    }

    public static void setText(CharSequence text, View parent, int viewId) {
        TextView view = findView(parent, viewId);
        view.setText(text);
    }

    public static void setText(CharSequence text, Activity activity, int viewId) {
        TextView view = findView(activity, viewId);
        view.setText(text);
    }

    public static void setOnClickListener(Activity activity, int viewId, View.OnClickListener listener) {
        View view = findView(activity, viewId);
        view.setOnClickListener(listener);
    }

    public static void setOnClickListener(View parent, int viewId, View.OnClickListener listener) {
        View view = findView(parent, viewId);
        view.setOnClickListener(listener);
    }

    public static void setVisible(boolean visible, View parent, int viewId) {
        View view = findView(parent, viewId);
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    public static void setVisible(boolean visible, Activity activity, int viewId) {
        View view = findView(activity, viewId);
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    public static void setBackgroundColor(int backgroundColor, View parent, int viewId) {
        View view = findView(parent, viewId);
        view.setBackgroundColor(backgroundColor);

    }

    public static void setImage(Bitmap bitmap, View parent, int viewId) {
        ImageView imageView = findView(parent, viewId);
        imageView.setImageBitmap(bitmap);
    }

    public static void setBackground(@DrawableRes int drawableId, View parent, int viewId) {
        View view = findView(parent, viewId);
        view.setBackgroundResource(drawableId);
    }

    public static void setImage(@DrawableRes int drawableId, View parent, @IdRes int viewId) {
        ImageView view = findView(parent, viewId);
        view.setImageResource(drawableId);
    }
}
