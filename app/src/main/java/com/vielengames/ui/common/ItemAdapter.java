package com.vielengames.ui.common;

import android.view.View;

public interface ItemAdapter {

    Object getItem();

    long getItemId();

    int getLayoutId();

    void bindView(View itemView);

    boolean isEnabled();

    int getItemViewType();
}
