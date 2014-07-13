package com.vielengames.ui;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.vielengames.VielenGamesApp;

public abstract class BaseFragment extends Fragment {

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        VielenGamesApp.inject(activity, this);
    }
}
