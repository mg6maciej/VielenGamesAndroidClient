package com.vielengames;

import com.vielengames.data.VielenGamesModel;
import com.vielengames.notification.move.MyMoveCheckAlarmManager;
import com.vielengames.notification.move.MyMoveNotificationManager;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public final class EventListeners {

    @Inject
    public EventListeners(
            VielenGamesModel model,
            SessionUpdatesHandler sessionUpdatesHandler,
            MyMoveCheckAlarmManager myMoveCheckAlarmManager,
            MyMoveNotificationManager myMoveNotificationManager) {
    }
}
