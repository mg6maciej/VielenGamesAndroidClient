package com.elpassion.vielengames.data.kuridor;

import android.graphics.Point;

import com.elpassion.vielengames.data.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mateusz on 16/06/2014.
 */
public class MoveValidator {
    private static final int MAP_SIZE = 9;

    public static boolean validateMove(KuridorGameState kuridorGameState, Player player,
                                       KuridorMove kuridorMove) {
        int moveX = PositionConverter.getX(kuridorMove.getPosition());
        int moveY = PositionConverter.getY(kuridorMove.getPosition());
        PositionConverter.Orientation orientation = PositionConverter.getOrientation(kuridorMove.getPosition());

        int opponentId = kuridorGameState.getPawns().get(0).getTeam() == player.getTeam() ? 1 : 0;
        int opponentX = PositionConverter.getX(kuridorGameState.getPawns().get(opponentId).getPosition());
        int opponentY = PositionConverter.getY(kuridorGameState.getPawns().get(opponentId).getPosition());

        int pawnId = kuridorGameState.getPawns().get(0).getTeam() == player.getTeam() ? 0 : 1;
        int pawnX = PositionConverter.getX(kuridorGameState.getPawns().get(pawnId).getPosition());
        int pawnY = PositionConverter.getY(kuridorGameState.getPawns().get(pawnId).getPosition());

        return kuridorMove.getMoveType() == KuridorMove.MoveType.pawn ?
                validatePawnMove(kuridorGameState, moveX, moveY, pawnX, pawnY,
                        opponentX, opponentY) :
                validateWallMove(kuridorGameState, moveX, moveY, orientation,
                        pawnId == 0 ? 0 : MAP_SIZE - 1, pawnX, pawnY,
                        opponentX, opponentY);
    }

    private static boolean validateWallMove(KuridorGameState kuridorGameState, int moveX, int moveY,
                                            PositionConverter.Orientation moveOrient, int finish,
                                            int pawnX, int pawnY, int opponentX, int opponentY) {
        if (moveOrient == PositionConverter.Orientation.none)
            return false;
        else if (moveOrient == PositionConverter.Orientation.hor) {
            if (moveX < 0 || moveX > MAP_SIZE - 2 || moveY < 0 || moveY > MAP_SIZE - 2)
                return false;
        } else if (moveOrient == PositionConverter.Orientation.ver) {
            if (moveX < 1 || moveX > MAP_SIZE - 1 || moveY < 1 || moveY > MAP_SIZE - 1)
                return false;
        }

        if (crossingWall(kuridorGameState, moveX, moveY, moveOrient))
            return false;

        if (reachedOppositeSide(kuridorGameState, pawnX, pawnY, finish))
            return true;

        return false;
    }

    private static boolean crossingWall(KuridorGameState kuridorGameState, int moveX, int moveY,
                                        PositionConverter.Orientation moveOrient) {
        WallPosition aborted;
        if (moveOrient == PositionConverter.Orientation.none)
            return true;
        else if (moveOrient == PositionConverter.Orientation.hor)
            aborted = WallPosition.builder().
                    position(PositionConverter.getPosition(
                            PositionConverter.Orientation.ver, moveX + 1, moveY + 1)).
                    build();
        else
            aborted = WallPosition.builder().
                    position(PositionConverter.getPosition(
                            PositionConverter.Orientation.hor, moveX - 1, moveY - 1)).
                    build();

        if (kuridorGameState.getWalls().contains(aborted))
            return true;

        if (moveOrient == PositionConverter.Orientation.hor)
            aborted = WallPosition.builder().
                    position(PositionConverter.getPosition(
                            PositionConverter.Orientation.hor, moveX - 1, moveY)).
                    build();
        else
            aborted = WallPosition.builder().
                    position(PositionConverter.getPosition(
                            PositionConverter.Orientation.ver, moveX, moveY - 1)).
                    build();

        if (kuridorGameState.getWalls().contains(aborted))
            return true;

        return false;
    }

    private static boolean reachedOppositeSide(KuridorGameState kuridorGameState,
                                               int startX, int startY, int finish) {
        List<Point> reached = new ArrayList<Point>();
        List<WallPosition> walls = kuridorGameState.getWalls();
        List<Point> newPositions = new ArrayList<Point>(4);
        reached.add(new Point(startX, startY));
        int oldSize = 0;

        while (oldSize != reached.size()) {
            oldSize = reached.size();

            for (Point t : reached)
                if (t.y == finish)
                    return true;

            newPositions.clear();
            for (Point t : reached) {
                if (validatePawnMove(kuridorGameState, t.x + 1, t.y, t.x, t.y, -1, -1))
                    newPositions.add(new Point(t.x + 1, t.y));
                if (validatePawnMove(kuridorGameState, t.x - 1, t.y, t.x, t.y, -1, -1))
                    newPositions.add(new Point(t.x - 1, t.y));
                if (validatePawnMove(kuridorGameState, t.x, t.y - 1, t.x, t.y, -1, -1))
                    newPositions.add(new Point(t.x, t.y - 1));
                if (validatePawnMove(kuridorGameState, t.x, t.y + 1, t.x, t.y, -1, -1))
                    newPositions.add(new Point(t.x, t.y + 1));
            }
            for (Point t : newPositions)
                if (!reached.contains(t))
                    reached.add(t);

            if (reached.size() >= MAP_SIZE * MAP_SIZE)
                return true;
        }

        return false;
    }


    private static boolean validatePawnMove(KuridorGameState kuridorGameState, int moveX, int moveY,
                                            int pawnX, int pawnY, int opponentX, int opponentY) {
        if (moveX < 0 || moveY < 0 || moveX >= MAP_SIZE || moveY >= MAP_SIZE)
            return false;

        if (moveX == opponentX && moveY == opponentY)
            return false;

        if (blockingWall(kuridorGameState, moveX, moveY, pawnX, pawnY, opponentX, opponentY))
            return false;

        if (Math.abs(pawnX - moveX) > 1 || Math.abs(pawnY - moveY) > 1) {
            if (Math.abs(opponentX - moveX) > 1 || Math.abs(opponentY - moveY) > 1)
                return false;
            else
                return validatePawnMove(kuridorGameState, moveX, moveY, opponentX, opponentY,
                        opponentX, opponentY);
        }

        return true;
    }

    private static boolean blockingWall(KuridorGameState kuridorGameState, int moveX, int moveY,
                                        int pawnX, int pawnY, int opponentX, int opponentY) {
        List<WallPosition> blockingWalls = new ArrayList<WallPosition>(2);
        PositionConverter.Orientation orientation;
                if (pawnY == moveY)
            orientation = PositionConverter.Orientation.ver;
        else if (pawnX == moveX)
            orientation = PositionConverter.Orientation.hor;
        else
            return false;

        if (kuridorGameState.getWalls().size() == 0)
            return false;

        if (pawnY > moveY) {
            blockingWalls.add(WallPosition.builder().
                    position(PositionConverter.getPosition(orientation, moveX - 1, moveY)).
                    build());
            blockingWalls.add(WallPosition.builder().
                    position(PositionConverter.getPosition(orientation, moveX, moveY)).
                    build());
        } else if (pawnY < moveY) {
            blockingWalls.add(WallPosition.builder().
                    position(PositionConverter.getPosition(orientation, moveX - 1, pawnY)).
                    build());
            blockingWalls.add(WallPosition.builder().
                    position(PositionConverter.getPosition(orientation, moveX, pawnY)).
                    build());
        } else if (pawnX > moveX) {
            blockingWalls.add(WallPosition.builder().
                    position(PositionConverter.getPosition(orientation, pawnX, moveY)).
                    build());
            blockingWalls.add(WallPosition.builder().
                    position(PositionConverter.getPosition(orientation, pawnX, moveY + 1)).
                    build());
        } else if (pawnX < moveX) {
            blockingWalls.add(WallPosition.builder().
                    position(PositionConverter.getPosition(orientation, moveX, moveY)).
                    build());
            blockingWalls.add(WallPosition.builder().
                    position(PositionConverter.getPosition(orientation, moveX, moveY + 1)).
                    build());
        }

        for (WallPosition w : kuridorGameState.getWalls()) {
            if (blockingWalls.get(0).equals(w) || blockingWalls.get(1).equals(w))
                return true;
        }

        return false;
    }
}
