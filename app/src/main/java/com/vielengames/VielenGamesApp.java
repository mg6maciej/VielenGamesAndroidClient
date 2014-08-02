package com.vielengames;

import android.app.Application;
import android.content.Context;

import com.crashlytics.android.Crashlytics;

import javax.inject.Inject;

import dagger.ObjectGraph;

public final class VielenGamesApp extends Application {

    @Inject
    EventListeners eventListeners;

    private ObjectGraph objectGraph;

    @Override
    public void onCreate() {
        super.onCreate();
        Crashlytics.start(this);
        initObjectGraph();
        inject(this);
    }

    private void initObjectGraph() {
        objectGraph = ObjectGraph.create(Modules.get(this));
    }

    public static void inject(Context context, Object object) {
        VielenGamesApp app = (VielenGamesApp) context.getApplicationContext();
        app.inject(object);
    }

    private void inject(Object object) {
        objectGraph.inject(object);
    }
}
