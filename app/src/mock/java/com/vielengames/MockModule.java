package com.vielengames;

import com.vielengames.api.MockVielenGamesApiImpl;
import com.vielengames.api.VielenGamesApi;
import com.vielengames.ui.login.LoginButtonProvider;
import com.vielengames.ui.login.StubLoginButtonProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        library = true,
        overrides = true
)
@SuppressWarnings("unused")
public final class MockModule {

    @Provides
    @Singleton
    public VielenGamesApi provideApi() {
        return new MockVielenGamesApiImpl();
    }

    @Provides
    public LoginButtonProvider provideLoginButtonProvider() {
        return new StubLoginButtonProvider();
    }
}
