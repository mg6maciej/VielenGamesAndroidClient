package com.vielengames.ui;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.vielengames.R;
import com.vielengames.utils.ViewUtils;

public final class GameHelpOverlayActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_help_overlay_activity);

        final ViewPager pager = ViewUtils.findView(this, R.id.game_help_overlay_pager);
        GameHelpOverlayPagerAdapter adapter = new GameHelpOverlayPagerAdapter(new GameHelpOverlayPagerAdapter.OnPageClickListener() {
            @Override
            public void onPageClick() {
                if (pager.getCurrentItem() == 2) {
                    finish();
                } else {
                    pager.setCurrentItem(pager.getCurrentItem() + 1, true);
                }
            }
        });
        pager.setAdapter(adapter);
    }
}
