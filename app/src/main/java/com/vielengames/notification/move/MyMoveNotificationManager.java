package com.vielengames.notification.move;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.app.NotificationCompat;

import com.vielengames.R;
import com.vielengames.VielenGamesPrefs;
import com.vielengames.data.Player;
import com.vielengames.data.Team;
import com.vielengames.data.kuridor.KuridorGame;
import com.vielengames.event.AppForegroundEvent;
import com.vielengames.event.MyMoveNotificationRequestEvent;
import com.vielengames.event.bus.EventBus;
import com.vielengames.ui.MainActivity;
import com.vielengames.utils.Circlifier;
import com.vielengames.utils.kuridor.KuridorGameStateDrawer;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public final class MyMoveNotificationManager {

    private static final int MY_MOVE_NOTIFICATION_ID = 696683;

    private final Context context;
    private final NotificationManager notificationManager;
    private final VielenGamesPrefs prefs;

    @Inject
    public MyMoveNotificationManager(Context context, NotificationManager notificationManager, VielenGamesPrefs prefs, EventBus eventBus) {
        this.context = context;
        this.notificationManager = notificationManager;
        this.prefs = prefs;
        eventBus.register(this);
    }

    @SuppressWarnings("unused")
    public void onEvent(MyMoveNotificationRequestEvent event) {
        createNotification(event.getGame());
    }

    private void createNotification(KuridorGame game) {
        Notification notification = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.notification_icon)
                .setLargeIcon(createIcon(game))
                .setTicker("Your move!")
                .setContentTitle("Your move!")
                .setContentText("No rush tho, thinking is important.")
                .setColor(context.getResources().getColor(android.R.color.black))
                .setContentIntent(createContentIntent(game))
                .build();
        notificationManager.notify(MY_MOVE_NOTIFICATION_ID, notification);
    }

    private Bitmap createIcon(KuridorGame game) {
        int bitmapSize = context.getResources().getDimensionPixelSize(R.dimen.common_image_size);
        Bitmap bitmap = Bitmap.createBitmap(bitmapSize, bitmapSize, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(0xFFFFFFFF);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        List<Player> players = game.getPlayers();
        Player team2Player = Team.SECOND.equals(players.get(0).getTeam()) ? players.get(0) : players.get(1);
        KuridorGameStateDrawer.Settings settings = new KuridorGameStateDrawer.Settings()
                .width(bitmapSize)
                .height(bitmapSize)
                .paint(paint)
                .dotsRadius(1.0f)
                .wallWidth(2.0f)
                .wallPadding(2.0f)
                .pawnPadding(2.0f)
                .team1Color(context.getResources().getColor(R.color.green_normal))
                .team2Color(context.getResources().getColor(R.color.blue_normal))
                .flip(prefs.getMe().equals(team2Player));
        KuridorGameStateDrawer.draw(game.getCurrentState(), canvas, settings);
        final int circleColor = 0xFF999999;
        final float circleWidth = context.getResources().getDimension(R.dimen.common_circle_width);
        return Circlifier.circlify(bitmap, circleColor, circleWidth);
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
