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
        float xPadding = settings.padding() + (canvas.getWidth() - size) / 2.0f;
        float yPadding = settings.padding() + (canvas.getHeight() - size) / 2.0f;
        for (int y = 0; y < 10; y++) {
            if (y == 0) {
                settings.paint().setColor(settings.team1Color());
            } else if (y == 9) {
                settings.paint().setColor(settings.team2Color());
            } else {
                settings.paint().setColor(0xFF000000);
            }
            for (int x = 0; x < 10; x++) {
                canvas.drawCircle(
                        xPadding + (size - 1 - 2 * settings.padding()) * x / 9.0f,
                        yPadding + (size - 1 - 2 * settings.padding()) * y / 9.0f,
                        y != 0 && y != 9
                                ? settings.dotsRadius()
                                : settings.lastLineDotsRadius(),
                        settings.paint());
            }
        }
        settings.paint().setColor(0xFF000000);
        settings.paint().setStrokeWidth(settings.wallWidth());
        for (String wall : state.getWalls()) {
            int centerX = wall.charAt(0) - 'a' + 1;
            int centerY = '8' - wall.charAt(1) + 1;
            int startX, startY, stopX, stopY;
            float wallPaddingX = 0.0f, wallPaddingY = 0.0f;
            if (wall.charAt(2) == 'h') {
                startX = centerX - 1;
                startY = centerY;
                stopX = centerX + 1;
                stopY = centerY;
                wallPaddingX = settings.wallPadding();
            } else {
                startX = centerY;
                startY = centerX - 1;
                stopX = centerY;
                stopY = centerX + 1;
                wallPaddingY = settings.wallPadding();
            }
            canvas.drawLine(
                    xPadding + (size - 1 - 2 * settings.padding()) * startX / 9.0f + wallPaddingX,
                    yPadding + (size - 1 - 2 * settings.padding()) * startY / 9.0f + wallPaddingY,
                    xPadding + (size - 1 - 2 * settings.padding()) * stopX / 9.0f - wallPaddingX,
                    yPadding + (size - 1 - 2 * settings.padding()) * stopY / 9.0f - wallPaddingY,
                    settings.paint());
        }
        settings.paint().setColor(settings.team1Color());
        String team1PawnPosition = state.getTeam1().getPawnPosition();
        int xTeam1 = 1 + 2 * (team1PawnPosition.charAt(0) - 'a');
        int yTeam1 = 1 + 2 * ('9' - team1PawnPosition.charAt(1));
        canvas.drawCircle(
                xPadding + (size - 1 - 2 * settings.padding()) * xTeam1 / 18.0f,
                yPadding + (size - 1 - 2 * settings.padding()) * yTeam1 / 18.0f,
                (size - 1 - 2 * settings.padding()) / 18.0f - settings.pawnPadding(),
                settings.paint());
        settings.paint().setColor(settings.team2Color());
        String team2PawnPosition = state.getTeam2().getPawnPosition();
        int xTeam2 = 1 + 2 * (team2PawnPosition.charAt(0) - 'a');
        int yTeam2 = 1 + 2 * ('9' - team2PawnPosition.charAt(1));
        canvas.drawCircle(
                xPadding + (size - 1 - 2 * settings.padding()) * xTeam2 / 18.0f,
                yPadding + (size - 1 - 2 * settings.padding()) * yTeam2 / 18.0f,
                (size - 1 - 2 * settings.padding()) / 18.0f - settings.pawnPadding(),
                settings.paint());
    }

    @Accessors(fluent = true)
    @Getter
    @Setter
    public static final class Settings {

        private Paint paint;
        private float padding;
        private float dotsRadius;
        private float lastLineDotsRadius;
        private float wallWidth;
        private float wallPadding;
        private float pawnPadding;
        private int team1Color;
        private int team2Color;
    }
}
