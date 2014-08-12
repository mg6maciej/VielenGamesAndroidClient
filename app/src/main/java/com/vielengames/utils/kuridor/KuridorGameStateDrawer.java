package com.vielengames.utils.kuridor;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

import com.vielengames.data.Team;
import com.vielengames.data.kuridor.KuridorGameState;
import com.vielengames.data.kuridor.KuridorMove;

import java.util.Collection;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public final class KuridorGameStateDrawer {

    private KuridorGameStateDrawer() {
    }

    public static void draw(KuridorGameState state, Canvas canvas, Settings settings) {
        boolean flip = settings.flip();
        int size = Math.min(settings.width(), settings.height());
        float xPadding = settings.padding() + (settings.width() - size) / 2.0f;
        float yPadding = settings.padding() + (settings.height() - size) / 2.0f;
        for (int y = 0; y < 10; y++) {
            if (y == 0 && !flip || y == 9 && flip) {
                settings.paint().setColor(settings.team1Color());
            } else if (y == 9 && !flip || y == 0 && flip) {
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
            if (flip) {
                centerX = 9 - centerX;
                centerY = 9 - centerY;
            }
            int startX, startY, stopX, stopY;
            float wallPaddingX = 0.0f, wallPaddingY = 0.0f;
            if (wall.charAt(2) == 'h') {
                startX = centerX - 1;
                startY = centerY;
                stopX = centerX + 1;
                stopY = centerY;
                wallPaddingX = settings.wallPadding();
            } else {
                startX = centerX;
                startY = centerY - 1;
                stopX = centerX;
                stopY = centerY + 1;
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
        if (flip) {
            xTeam1 = 18 - xTeam1;
            yTeam1 = 18 - yTeam1;
        }
        canvas.drawCircle(
                xPadding + (size - 1 - 2 * settings.padding()) * xTeam1 / 18.0f,
                yPadding + (size - 1 - 2 * settings.padding()) * yTeam1 / 18.0f,
                (size - 1 - 2 * settings.padding()) / 18.0f - settings.pawnPadding(),
                settings.paint());
        settings.paint().setColor(settings.team2Color());
        String team2PawnPosition = state.getTeam2().getPawnPosition();
        int xTeam2 = 1 + 2 * (team2PawnPosition.charAt(0) - 'a');
        int yTeam2 = 1 + 2 * ('9' - team2PawnPosition.charAt(1));
        if (flip) {
            xTeam2 = 18 - xTeam2;
            yTeam2 = 18 - yTeam2;
        }
        canvas.drawCircle(
                xPadding + (size - 1 - 2 * settings.padding()) * xTeam2 / 18.0f,
                yPadding + (size - 1 - 2 * settings.padding()) * yTeam2 / 18.0f,
                (size - 1 - 2 * settings.padding()) / 18.0f - settings.pawnPadding(),
                settings.paint());
        if (settings.drawLegalPawnMoves()) {
            int activeTeamColor = Team.FIRST.equals(state.getActiveTeam()) ? settings.team1Color() : settings.team2Color();
            activeTeamColor &= 0x80FFFFFF;
            settings.paint().setColor(activeTeamColor);
            String pawnPosition = state.getActiveTeamPawnPosition();
            Collection<KuridorMove> legalMoves = state.getLegalPawnMoves();
            for (KuridorMove move : legalMoves) {
                int xActive = 1 + 2 * (pawnPosition.charAt(0) - 'a');
                int yActive = 1 + 2 * ('9' - pawnPosition.charAt(1));
                int xDestination = 1 + 2 * (move.getPosition().charAt(0) - 'a');
                int yDestination = 1 + 2 * ('9' - move.getPosition().charAt(1));
                String oppPosition = state.getInactiveTeamsPawnPositions().iterator().next();
                int xIntermediate = 1 + 2 * (oppPosition.charAt(0) - 'a');
                int yIntermediate = 1 + 2 * ('9' - oppPosition.charAt(1));
                if (flip) {
                    xActive = 18 - xActive;
                    yActive = 18 - yActive;
                    xDestination = 18 - xDestination;
                    yDestination = 18 - yDestination;
                    xIntermediate = 18 - xIntermediate;
                    yIntermediate = 18 - yIntermediate;
                }
                ArrowDirection direction = ArrowDirection.fromPoints(xActive, yActive, xDestination, yDestination, xIntermediate, yIntermediate);
                Path path = new Path();
                path.moveTo(xPadding + (size - 1 - 2 * settings.padding()) * xActive / 18.0f,
                        yPadding + (size - 1 - 2 * settings.padding()) * yActive / 18.0f);
                for (float[] arrowPosition : direction.arrowPositions) {
                    if (arrowPosition == direction.arrowPositions[0] && !(xActive == xDestination || yActive == yDestination)) {
                        path.quadTo(xPadding + (size - 1 - 2 * settings.padding()) * xIntermediate / 18.0f,
                                yPadding + (size - 1 - 2 * settings.padding()) * yIntermediate / 18.0f,
                                xPadding + (size - 1 - 2 * settings.padding()) * (xDestination + arrowPosition[0]) / 18.0f,
                                yPadding + (size - 1 - 2 * settings.padding()) * (yDestination + arrowPosition[1]) / 18.0f);
                    } else {
                        path.lineTo(xPadding + (size - 1 - 2 * settings.padding()) * (xDestination + arrowPosition[0]) / 18.0f,
                                yPadding + (size - 1 - 2 * settings.padding()) * (yDestination + arrowPosition[1]) / 18.0f);
                    }
                }
                if (!(xActive == xDestination || yActive == yDestination)) {
                    path.quadTo(xPadding + (size - 1 - 2 * settings.padding()) * xIntermediate / 18.0f,
                            yPadding + (size - 1 - 2 * settings.padding()) * yIntermediate / 18.0f,
                            xPadding + (size - 1 - 2 * settings.padding()) * xActive / 18.0f,
                            yPadding + (size - 1 - 2 * settings.padding()) * yActive / 18.0f);
                } else {
                    path.lineTo(xPadding + (size - 1 - 2 * settings.padding()) * xActive / 18.0f,
                            yPadding + (size - 1 - 2 * settings.padding()) * yActive / 18.0f);
                }
                canvas.drawPath(path, settings.paint());
            }
        }
    }

    private enum ArrowDirection {

        LEFT(new float[][]{{0.45f, 0.15f}, {0.45f, 0.5f}, {-0.4f, 0.0f}, {0.45f, -0.5f}, {0.45f, -0.15f}}),
        RIGHT(new float[][]{{-0.45f, -0.15f}, {-0.45f, -0.5f}, {0.4f, 0.0f}, {-0.45f, 0.5f}, {-0.45f, 0.15f}}),
        UP(new float[][]{{0.15f, 0.45f}, {0.5f, 0.45f}, {0.0f, -0.4f}, {-0.5f, 0.45f}, {-0.15f, 0.45f}}),
        DOWN(new float[][]{{-0.15f, -0.45f}, {-0.5f, -0.45f}, {0.0f, 0.4f}, {0.5f, -0.45f}, {0.15f, -0.45f}});

        private final float[][] arrowPositions;

        ArrowDirection(float[][] arrowPositions) {
            this.arrowPositions = arrowPositions;
        }

        public static ArrowDirection fromPoints(int x1, int y1, int x2, int y2, int x3, int y3) {
            ArrowDirection direction = fromClosePoints(x1, y1, x2, y2);
            if (direction != null) {
                return direction;
            }
            direction = fromClosePoints(x3, y3, x2, y2);
            if (direction != null) {
                return direction;
            }
            throw new IllegalStateException();
        }

        private static ArrowDirection fromClosePoints(int x1, int y1, int x2, int y2) {
            if (y1 == y2) {
                if (x1 < x2) {
                    return RIGHT;
                } else {
                    return LEFT;
                }
            } else if (x1 == x2) {
                if (y1 < y2) {
                    return DOWN;
                } else {
                    return UP;
                }
            } else {
                return null;
            }
        }
    }

    @Accessors(fluent = true)
    @Getter
    @Setter
    public static final class Settings {

        private int width;
        private int height;
        private Paint paint;
        private float padding;
        private float dotsRadius;
        private float lastLineDotsRadius;
        private float wallWidth;
        private float wallPadding;
        private float pawnPadding;
        private int team1Color;
        private int team2Color;
        private boolean drawLegalPawnMoves;
        private boolean flip;
    }
}
