package com.vielengames.notification.move;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.vielengames.R;
import com.vielengames.data.kuridor.KuridorGame;
import com.vielengames.event.AppForegroundEvent;
import com.vielengames.event.MyMoveNotificationRequestEvent;
import com.vielengames.event.bus.EventBus;
import com.vielengames.ui.MainActivity;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public final class MyMoveNotificationManager {

    private static final int MY_MOVE_NOTIFICATION_ID = 696683;

    private final Context context;
    private final NotificationManager notificationManager;

    @Inject
    public MyMoveNotificationManager(Context context, NotificationManager notificationManager, EventBus eventBus) {
        this.context = context;
        this.notificationManager = notificationManager;
        eventBus.register(this);
    }

    @SuppressWarnings("unused")
    public void onEvent(MyMoveNotificationRequestEvent event) {
        createNotification(event.getGame());
    }

    private void createNotification(KuridorGame game) {
        Notification notification = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.app_icon)
                .setTicker("Your move!")
                .setContentTitle("Your move!")
                .setContentText("No rush tho, thinking is important.")
                .setContentIntent(createContentIntent(game))
                .build();
        notificationManager.notify(MY_MOVE_NOTIFICATION_ID, notification);
    }

    private PendingIntent createContentIntent(KuridorGame game) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("game", game);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
    }

    @SuppressWarnings("unused")
    public void onEvent(AppForegroundEvent event) {
        cancelNotification();
    }

    private void cancelNotification() {
        notificationManager.cancel(MY_MOVE_NOTIFICATION_ID);
    }
}
