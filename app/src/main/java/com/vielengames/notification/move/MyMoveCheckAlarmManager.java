package com.vielengames.notification.move;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

import com.vielengames.VielenGamesPrefs;
import com.vielengames.event.AppBackgroundEvent;
import com.vielengames.event.AppForegroundEvent;
import com.vielengames.event.MyMoveCheckAlarmRequestEvent;
import com.vielengames.event.bus.EventBus;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public final class MyMoveCheckAlarmManager {

    private static final int HALF_HOUR = 30 * 60 * 1000;

    private final Context context;
    private final AlarmManager alarmManager;
    private final VielenGamesPrefs prefs;

    @Inject
    public MyMoveCheckAlarmManager(Context context, AlarmManager alarmManager, VielenGamesPrefs prefs, EventBus eventBus) {
        this.context = context;
        this.alarmManager = alarmManager;
        this.prefs = prefs;
        eventBus.register(this);
    }

    @SuppressWarnings("unused")
    public void onEvent(AppBackgroundEvent event) {
        if (prefs.isSignedIn()) {
            setAlarm();
        }
    }

    @SuppressWarnings("unused")
    public void onEvent(AppForegroundEvent event) {
        cancelAlarm();
    }

    @SuppressWarnings("unused")
    public void onEvent(MyMoveCheckAlarmRequestEvent event) {
        setAlarm();
    }

    private void setAlarm() {
        alarmManager.set(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime() + HALF_HOUR, getOperation());
    }

    private void cancelAlarm() {
        alarmManager.cancel(getOperation());
    }

    private PendingIntent getOperation() {
        Intent intent = new Intent(context, MyMoveCheckBroadcastReceiver.class);
        return PendingIntent.getBroadcast(context, 0, intent, 0);
    }
}
