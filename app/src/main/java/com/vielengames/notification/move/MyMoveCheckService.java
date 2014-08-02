package com.vielengames.notification.move;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.vielengames.SessionUpdatesHandler;
import com.vielengames.VielenGamesApp;
import com.vielengames.VielenGamesPrefs;
import com.vielengames.data.Game;
import com.vielengames.data.kuridor.KuridorGame;
import com.vielengames.event.MyMoveCheckAlarmRequestEvent;
import com.vielengames.event.MyMoveNotificationRequestEvent;
import com.vielengames.event.SessionUpdatesFailedEvent;
import com.vielengames.event.SessionUpdatesResponseEvent;
import com.vielengames.event.bus.EventBus;

import java.util.List;

import javax.inject.Inject;

public final class MyMoveCheckService extends Service {

    @Inject
    SessionUpdatesHandler updatesHandler;
    @Inject
    EventBus eventBus;
    @Inject
    VielenGamesPrefs prefs;

    @Override
    public void onCreate() {
        super.onCreate();
        VielenGamesApp.inject(this, this);
        eventBus.register(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        updatesHandler.requestUpdates();
        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        eventBus.unregister(this);
    }

    @SuppressWarnings("unused")
    public void onEvent(SessionUpdatesResponseEvent event) {
        KuridorGame game = getGameWithMyMove(event.getUpdates().getGames());
        if (game != null) {
            eventBus.post(new MyMoveNotificationRequestEvent(game));
        } else {
            eventBus.post(new MyMoveCheckAlarmRequestEvent());
        }
        stopSelf();
    }

    @SuppressWarnings("unused")
    public void onEvent(SessionUpdatesFailedEvent event) {
        eventBus.post(new MyMoveCheckAlarmRequestEvent());
        stopSelf();
    }

    private KuridorGame getGameWithMyMove(List<Game> games) {
        for (Game game : games) {
            KuridorGame kuridorGame = (KuridorGame) game;
            if (prefs.getMe().equals(kuridorGame.getActivePlayer())) {
                return kuridorGame;
            }
        }
        return null;
    }
}
