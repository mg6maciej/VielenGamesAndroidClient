package com.elpassion.vielengames.data.kuridor;

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

        int opponentId = kuridorGameState.getPawns().get(0).getTeam() == player.getTeam() ? 0 : 1;
        int opponentX = PositionConverter.getX(kuridorGameState.getPawns().get(opponentId).position);
        int opponentY = PositionConverter.getY(kuridorGameState.getPawns().get(opponentId).position);

        int pawnId = kuridorGameState.getPawns().get(0).getTeam() == player.getTeam() ? 0 : 1;
        int pawnX = PositionConverter.getX(kuridorGameState.getPawns().get(pawnId).position);
        int pawnY = PositionConverter.getY(kuridorGameState.getPawns().get(pawnId).position);

        return kuridorMove.getMoveType() == KuridorMove.MoveType.pawn ?
                validatePawnMove(kuridorGameState, moveX, moveY, pawnX, pawnY,
                        opponentX, opponentY) :
                validateWallMove(kuridorGameState, moveX, moveY, orientation, pawnId == 0 ? 0 : MAP_SIZE - 1, pawnX, pawnY,
                        opponentX, opponentY);
    }

    private static boolean validateWallMove(KuridorGameState kuridorGameState, int moveX, int moveY,
                                            PositionConverter.Orientation moveOrient, int finish,
                                            int pawnX, int pawnY, int opponentX, int opponentY) {
        if (moveOrient == PositionConverter.Orientation.none)
            return false;
        else if (moveOrient == PositionConverter.Orientation.hor) {
            if (moveX < 0 || moveX >= MAP_SIZE - 2 || moveY < 0 || moveY >= MAP_SIZE - 1)
                return false;
        } else if (moveOrient == PositionConverter.Orientation.ver) {
            if (moveX < 1 || moveX >= MAP_SIZE || moveY < 2 || moveY >= MAP_SIZE)
                return false;
        }

        //krzyżyjące się ściany

        if (reachedOppositeSide(kuridorGameState, pawnX, pawnY, finish))
            return true;

        return false;
    }

    private static boolean reachedOppositeSide(KuridorGameState kuridorGameState,
                                               int startX, int startY, int finish) {
        List<Tuple> reached = new ArrayList<Tuple>();
        List<WallPosition> walls = kuridorGameState.getWalls();
        reached.add(new Tuple(startX, startY));

        for (Tuple t : reached)
            if (t.y == finish)
                return true;

        if (reached.size() >= MAP_SIZE * MAP_SIZE)
            return false;

        List<Tuple> newPositions = new ArrayList<Tuple>(4);
        for (Tuple t : reached)
        {
            if (validatePawnMove(kuridorGameState, t.x + 1, t.y + 1, t.x, t.y, -1, -1))
                newPositions.add(new Tuple(t.x + 1, t.y + 1));
            if (validatePawnMove(kuridorGameState, t.x - 1, t.y - 1, t.x, t.y, -1, -1))
                newPositions.add(new Tuple(t.x - 1, t.y - 1));
            if (validatePawnMove(kuridorGameState, t.x + 1, t.y - 1, t.x, t.y, -1, -1))
                newPositions.add(new Tuple(t.x + 1, t.y - 1));
            if (validatePawnMove(kuridorGameState, t.x - 1, t.y + 1, t.x, t.y, -1, -1))
                newPositions.add(new Tuple(t.x - 1, t.y + 1));
        }

        return false;
    }


    private static boolean validatePawnMove(KuridorGameState kuridorGameState, int moveX, int moveY,
                                            int pawnX, int pawnY, int opponentX, int opponentY) {
        if (moveX < 0 || moveY < 0 || moveX >= MAP_SIZE || moveY >= MAP_SIZE)
            return false;

        if (blockingWall(kuridorGameState, moveX, moveY, pawnX, pawnY, opponentX, opponentY))
            return false;

        if (1 == (Math.abs(opponentX - moveX) + Math.abs(opponentY - moveY)) &&
                1 == (Math.abs(opponentX - pawnX) + Math.abs(opponentY - pawnY)))
            return validatePawnMove(kuridorGameState, moveX, moveY, opponentX, opponentY,
                    opponentX, opponentY);

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
            return true;

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
            if (blockingWalls.get(0) == w || blockingWalls.get(1) == w)
                return true;
        }

        return false;
    }

    private static class Tuple
    {
        int x;
        int y;

        Tuple(int x, int y)
        {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Tuple tuple = (Tuple) o;

            if (x != tuple.x) return false;
            if (y != tuple.y) return false;

            return true;
        }
    }
}
