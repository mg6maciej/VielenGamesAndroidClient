package com.vielengames.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

public abstract class BaseView extends View {

    protected BaseView(Context context) {
        super(context);
    }

    protected BaseView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected BaseView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
