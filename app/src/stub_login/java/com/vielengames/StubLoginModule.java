package com.vielengames;

import com.vielengames.ui.login.LoginButtonProvider;
import com.vielengames.ui.login.StubLoginButtonProvider;

import dagger.Module;
import dagger.Provides;

@Module(
        library = true,
        overrides = true
)
@SuppressWarnings("unused")
public final class StubLoginModule {

    @Provides
    public LoginButtonProvider provideLoginButtonProvider() {
        return new StubLoginButtonProvider();
    }
}
