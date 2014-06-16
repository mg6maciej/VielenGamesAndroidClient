package com.elpassion.vielengames.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.elpassion.vielengames.R;
import com.elpassion.vielengames.api.GooglePlusAuth;
import com.elpassion.vielengames.api.VielenGamesClient;

import javax.inject.Inject;

public final class LoginActivity extends BaseActivity implements View.OnClickListener {

    @Inject
    GooglePlusAuth googlePlusAuth;
    @Inject
    VielenGamesClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        findViewById(R.id.button_login).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        Toast.makeText(this, getString(R.string.not_implemented_yet), Toast.LENGTH_SHORT).show();
    }
}
