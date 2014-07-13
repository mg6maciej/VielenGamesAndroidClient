package com.vielengames.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public abstract class BaseLinearLayout extends LinearLayout {

    protected BaseLinearLayout(Context context) {
        super(context);
    }

    protected BaseLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
}
