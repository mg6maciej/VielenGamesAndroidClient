package com.vielengames.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

import com.vielengames.R;
import com.vielengames.VielenGamesApp;
import com.vielengames.utils.ViewUtils;

public abstract class BaseActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        VielenGamesApp.inject(this, this);
    }

    protected void setupToolbar() {
        Toolbar toolbar = ViewUtils.findView(this, R.id.app_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.app_logo);
    }
}
