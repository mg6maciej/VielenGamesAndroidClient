package com.elpassion.vielengames.ui;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

public final class MainPagerAdapter extends FragmentPagerAdapter {

    private GameProposalsFragment gameProposalsFragment = new GameProposalsFragment();
    private MyGamesFragment myGamesFragment = new MyGamesFragment();

    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public BaseFragment getItem(int position) {
        if (position == 0) {
            return gameProposalsFragment;
        }
        if (position == 1) {
            return myGamesFragment;
        }
        throw new IllegalStateException();
    }

    public void requestGameProposals() {
        gameProposalsFragment.requestGameProposals();
    }
}
