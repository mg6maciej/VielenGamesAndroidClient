package com.elpassion.vielengames.utils;

import android.view.View;
import android.widget.TextView;

public final class ViewUtils {

    private ViewUtils() {
    }

    public static <T> T findView(View parent, int viewId) {
        return (T) parent.findViewById(viewId);
    }

    public static void setText(String text, View parent, int viewId) {
        TextView view = findView(parent, viewId);
        view.setText(text);
    }
}
