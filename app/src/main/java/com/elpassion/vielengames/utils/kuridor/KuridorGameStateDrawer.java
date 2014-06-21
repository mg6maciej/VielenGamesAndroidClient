package com.elpassion.vielengames.utils.kuridor;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.elpassion.vielengames.data.kuridor.KuridorGameState;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public final class KuridorGameStateDrawer {

    private KuridorGameStateDrawer() {
    }

    public static void draw(KuridorGameState state, Canvas canvas, Settings settings) {
        int size = Math.min(canvas.getWidth(), canvas.getHeight());
        int xPadding = settings.padding() + (canvas.getWidth() - size) / 2;
        int yPadding = settings.padding() + (canvas.getHeight() - size) / 2;
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                canvas.drawCircle(
                        xPadding + (size - 1 - 2 * settings.padding()) * x / 9.0f,
                        yPadding + (size - 1 - 2 * settings.padding()) * y / 9.0f,
                        1,
                        settings.paint());
            }
        }
        for (String wall : state.getWalls()) {
            int centerX = wall.charAt(0) - 'a' + 1;
            int centerY = '8' - wall.charAt(1) + 1;
            int startX, startY, stopX, stopY;
            if (wall.charAt(2) == 'h') {
                startX = centerX - 1;
                startY = centerY;
                stopX = centerX + 1;
                stopY = centerY;
            } else {
                startX = centerY;
                startY = centerX - 1;
                stopX = centerY;
                stopY = centerX + 1;
            }
            canvas.drawLine(
                    xPadding + (size - 1 - 2 * settings.padding()) * startX / 9.0f,
                    yPadding + (size - 1 - 2 * settings.padding()) * startY / 9.0f,
                    xPadding + (size - 1 - 2 * settings.padding()) * stopX / 9.0f,
                    yPadding + (size - 1 - 2 * settings.padding()) * stopY / 9.0f,
                    settings.paint());
        }
        settings.paint().setColor(settings.team1Color());
        String team1PawnPosition = state.getTeam1().getPawnPosition();
        int xTeam1 = 1 + 2 * (team1PawnPosition.charAt(0) - 'a');
        int yTeam1 = 1 + 2 * ('9' - team1PawnPosition.charAt(1));
        canvas.drawCircle(
                xPadding + (size - 1 - 2 * settings.padding()) * xTeam1 / 18.0f,
                yPadding + (size - 1 - 2 * settings.padding()) * yTeam1 / 18.0f,
                10,
                settings.paint()
        );
        settings.paint().setColor(settings.team2Color());
        String team2PawnPosition = state.getTeam2().getPawnPosition();
        int xTeam2 = 1 + 2 * (team2PawnPosition.charAt(0) - 'a');
        int yTeam2 = 1 + 2 * ('9' - team2PawnPosition.charAt(1));
        canvas.drawCircle(
                xPadding + (size - 1 - 2 * settings.padding()) * xTeam2 / 18.0f,
                yPadding + (size - 1 - 2 * settings.padding()) * yTeam2 / 18.0f,
                10,
                settings.paint()
        );
    }

    @Accessors(fluent = true)
    @Getter
    @Setter
    public static final class Settings {

        private int padding;
        private Paint paint;
        private int team1Color;
        private int team2Color;
    }
}
