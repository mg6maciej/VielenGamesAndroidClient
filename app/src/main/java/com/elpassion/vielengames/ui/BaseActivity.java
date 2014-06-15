package com.elpassion.vielengames.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.elpassion.vielengames.VielenGamesApp;

public abstract class BaseActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        VielenGamesApp.inject(this, this);
    }
}
