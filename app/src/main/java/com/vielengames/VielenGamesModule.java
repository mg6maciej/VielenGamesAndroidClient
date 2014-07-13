package com.vielengames;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vielengames.api.VielenGamesApi;
import com.vielengames.api.VielenGamesClient;
import com.vielengames.data.Game;
import com.vielengames.event.bus.EventBus;
import com.vielengames.event.bus.GreenRobotEventBus;
import com.vielengames.parser.GameJsonDeserializer;
import com.vielengames.ui.AboutActivity;
import com.vielengames.ui.GameActivity;
import com.vielengames.ui.GameHelpOverlayActivity;
import com.vielengames.ui.GameProposalsFragment;
import com.vielengames.ui.LauncherActivity;
import com.vielengames.ui.LoginActivity;
import com.vielengames.ui.MainActivity;
import com.vielengames.ui.MyGamesFragment;
import com.vielengames.ui.ResultOverlayActivity;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

@Module(
        injects = {
                VielenGamesApp.class,
                LauncherActivity.class,
                LoginActivity.class,
                MainActivity.class,
                GameProposalsFragment.class,
                MyGamesFragment.class,
                GameActivity.class,
                AboutActivity.class,
                ResultOverlayActivity.class,
                GameHelpOverlayActivity.class
        }
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
    public VielenGamesPrefs providePrefs(SharedPreferences sharedPrefs, Gson gson) {
        return new VielenGamesPrefs(sharedPrefs, gson);
    }


    @Provides
    @Singleton
    public EventBus provideEventBus() {
        return new GreenRobotEventBus();
    }

    @Provides
    @Singleton
    public Gson provideGson() {
        return new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .registerTypeAdapter(Game.class, new GameJsonDeserializer())
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
                requestFacade.addHeader("X-Auth-Token", prefs.getToken());
                requestFacade.addHeader("X-Client-Version", "1");
                requestFacade.addHeader("X-Supported-Game-Types", "kuridor");
            }
        };
    }

    @Provides
    @Singleton
    public VielenGamesClient provideClient(EventBus eventBus, VielenGamesApi api, VielenGamesPrefs prefs) {
        return new VielenGamesClient(eventBus, api, prefs);
    }

    @Provides
    @Singleton
    public ForegroundNotifier provideForegroundNotifier(EventBus eventBus) {
        return new ForegroundNotifier(eventBus);
    }
}
