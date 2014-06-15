package com.elpassion.vielengames.ui;

import com.elpassion.vielengames.api.GooglePlusAuth;
import com.elpassion.vielengames.api.VielenGamesClient;

import javax.inject.Inject;

public final class LoginActivity extends BaseActivity {

    @Inject
    GooglePlusAuth googlePlusAuth;
    @Inject
    VielenGamesClient client;
}
