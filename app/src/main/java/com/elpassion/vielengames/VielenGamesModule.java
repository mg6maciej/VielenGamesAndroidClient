package com.elpassion.vielengames;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.elpassion.vielengames.api.GooglePlusAuth;
import com.elpassion.vielengames.api.VielenGamesApi;
import com.elpassion.vielengames.api.VielenGamesClient;
import com.elpassion.vielengames.event.bus.EventBus;
import com.elpassion.vielengames.event.bus.GreenRobotEventBus;
import com.elpassion.vielengames.ui.GameActivity;
import com.elpassion.vielengames.ui.LauncherActivity;
import com.elpassion.vielengames.ui.LoginActivity;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

@Module(
        injects = {
                LauncherActivity.class,
                LoginActivity.class,
                GameActivity.class
        },
        library = true
)
@SuppressWarnings("unused")
public final class VielenGamesModule {

    private final Context context;

    public VielenGamesModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    public SharedPreferences provideSharedPrefs() {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Provides
    @Singleton
    public VielenGamesPrefs providePrefs(SharedPreferences sharedPrefs) {
        return new VielenGamesPrefs(sharedPrefs);
    }

    @Provides
    @Singleton
    public EventBus provideEventBus() {
        return new GreenRobotEventBus();
    }

    @Provides
    @Singleton
    public GooglePlusAuth provideGooglePlusAuth() {
        return null;
    }

    @Provides
    @Singleton
    public Gson provideGson() {
        return new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
    }

    @Provides
    @Singleton
    public VielenGamesApi provideApi(Gson gson, VielenGamesPrefs prefs) {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(BuildConfig.ENDPOINT)
                .setRequestInterceptor(createRequestInterceptor(prefs))
                .setConverter(new GsonConverter(gson))
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        return restAdapter.create(VielenGamesApi.class);
    }

    private RequestInterceptor createRequestInterceptor(final VielenGamesPrefs prefs) {
        return new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade requestFacade) {
                requestFacade.addQueryParam("token", prefs.getToken());
            }
        };
    }

    @Provides
    @Singleton
    public VielenGamesClient provideClient(EventBus eventBus, VielenGamesApi api) {
        return new VielenGamesClient(eventBus, api);
    }
}
