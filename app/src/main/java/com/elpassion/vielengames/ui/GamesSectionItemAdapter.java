package com.elpassion.vielengames.ui;

import android.view.View;
import android.widget.TextView;

import com.elpassion.vielengames.R;
import com.elpassion.vielengames.ui.common.ItemAdapter;
import com.elpassion.vielengames.utils.ViewUtils;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public final class GamesSectionItemAdapter implements ItemAdapter {

    private final String text;

    @Override
    public String getItem() {
        return text;
    }

    @Override
    public long getItemId() {
        return 0;
    }

    @Override
    public int getLayoutId() {
        return R.layout.games_section_item;
    }

    @Override
    public void bindView(View itemView) {
        ViewUtils.setText(text, itemView, R.id.games_section_text);
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public int getItemViewType() {
        return 1;
    }
}
