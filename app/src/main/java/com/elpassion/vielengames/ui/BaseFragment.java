package com.elpassion.vielengames.ui;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.elpassion.vielengames.VielenGamesApp;

public abstract class BaseFragment extends Fragment {

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        VielenGamesApp.inject(activity, this);
    }
}
