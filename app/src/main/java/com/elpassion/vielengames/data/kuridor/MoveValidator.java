package com.elpassion.vielengames.data.kuridor;

import com.elpassion.vielengames.data.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mateusz on 16/06/2014.
 */
public class MoveValidator {
    private static final int MAP_SIZE = 9;

    public static boolean validateMove(KuridorGameState kuridorGameState, Player player, KuridorMove kuridorMove) {
        return kuridorMove.getMoveType() == KuridorMove.MoveType.pawn ?
                validatePawnMove(kuridorGameState, player, kuridorMove) :
                validateWallMove(kuridorGameState, player, kuridorMove);
    }

    private static boolean validateWallMove(KuridorGameState kuridorGameState, Player player, KuridorMove kuridorMove) {
        return false;
    }

    private static boolean validatePawnMove(KuridorGameState kuridorGameState, Player player, KuridorMove kuridorMove) {
        int moveX = PositionConverter.getX(kuridorMove.getPosition());
        int moveY = PositionConverter.getY(kuridorMove.getPosition());

        if (moveX < 0 || moveY < 0 || moveX >= MAP_SIZE || moveY >= MAP_SIZE)
            return false;

        if (blockingWall(kuridorGameState, player, moveX, moveY))
            return false;


        return false;
    }

    private static boolean blockingWall(KuridorGameState kuridorGameState, Player player, int moveX, int moveY) {
        int pawnId = kuridorGameState.getPawns().get(0).getTeam() == player.getTeam() ? 0 : 1;
        int pawnX = PositionConverter.getX(kuridorGameState.getPawns().get(pawnId).getPosition());
        int pawnY = PositionConverter.getY(kuridorGameState.getPawns().get(pawnId).getPosition());

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
}
