package com.elpassion.vielengames.utils;

import android.app.Activity;
import android.view.View;
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

    public static void setBackgroundColor(int backgroundColor, View parent, int viewId){
        View view = findView(parent, viewId);
        view.setBackgroundColor(backgroundColor);

    }
}
