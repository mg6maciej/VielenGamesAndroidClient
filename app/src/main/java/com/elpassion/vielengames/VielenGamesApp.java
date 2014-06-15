package com.elpassion.vielengames;

import android.app.Application;
import android.content.Context;

import dagger.ObjectGraph;

public final class VielenGamesApp extends Application {

    private ObjectGraph objectGraph;

    @Override
    public void onCreate() {
        super.onCreate();
        initObjectGraph();
    }

    private void initObjectGraph() {
        objectGraph = ObjectGraph.create(Modules.get(this));
    }

    public static void inject(Context context, Object object) {
        VielenGamesApp app = (VielenGamesApp) context.getApplicationContext();
        app.objectGraph.inject(object);
    }
}
