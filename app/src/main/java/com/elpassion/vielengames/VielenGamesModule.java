package com.elpassion.vielengames;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.elpassion.vielengames.api.GooglePlusAuth;
import com.elpassion.vielengames.api.GooglePlusAuthImpl;
import com.elpassion.vielengames.api.VielenGamesApi;
import com.elpassion.vielengames.api.VielenGamesClient;
import com.elpassion.vielengames.data.Game;
import com.elpassion.vielengames.event.bus.EventBus;
import com.elpassion.vielengames.event.bus.GreenRobotEventBus;
import com.elpassion.vielengames.parser.GameJsonDeserializer;
import com.elpassion.vielengames.ui.GameActivity;
import com.elpassion.vielengames.ui.GameProposalsFragment;
import com.elpassion.vielengames.ui.LauncherActivity;
import com.elpassion.vielengames.ui.LoginActivity;
import com.elpassion.vielengames.ui.MainActivity;
import com.elpassion.vielengames.ui.MyGamesFragment;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.plus.Plus;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import lombok.core.PrintAST;
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
    public GooglePlusAuth provideGooglePlusAuth(GoogleApiClient googleApiClient, EventBus eventBus) {
        return new GooglePlusAuthImpl(googleApiClient, eventBus);
    }

    @Provides
    @Singleton
    public GoogleApiClient provideGoogleApiClient(){
        return  new GoogleApiClient.Builder(context)
//                .addConnectionCallbacks(this)
//                .addOnConnectionFailedListener(this)
                .addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_PROFILE)
                .build();
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
}
