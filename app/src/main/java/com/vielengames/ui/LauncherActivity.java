package com.vielengames.ui;

import android.content.Intent;
import android.os.Bundle;

import com.vielengames.VielenGamesPrefs;

import javax.inject.Inject;

public final class LauncherActivity extends BaseActivity {

    @Inject
    VielenGamesPrefs prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(this, getNextActivityClass());
        startActivity(intent);
        finish();
    }

    private Class<? extends BaseActivity> getNextActivityClass() {
        if (prefs.isSignedIn()) {
            return MainActivity.class;
        } else {
            return LoginActivity.class;
        }
    }
}
