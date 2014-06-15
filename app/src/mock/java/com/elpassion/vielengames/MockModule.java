package com.elpassion.vielengames;

import com.elpassion.vielengames.api.GooglePlusAuth;
import com.elpassion.vielengames.api.MockGooglePlusAuthImpl;
import com.elpassion.vielengames.api.MockVielenGamesApiImpl;
import com.elpassion.vielengames.api.MockVielenGamesAuthApiImpl;
import com.elpassion.vielengames.api.VielenGamesApi;
import com.elpassion.vielengames.api.VielenGamesAuthApi;

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
    public GooglePlusAuth provideGooglePlusAuth() {
        return new MockGooglePlusAuthImpl();
    }

    @Provides
    @Singleton
    public VielenGamesAuthApi provideAuthApi() {
        return new MockVielenGamesAuthApiImpl();
    }

    @Provides
    @Singleton
    public VielenGamesApi provideApi() {
        return new MockVielenGamesApiImpl();
    }
}
