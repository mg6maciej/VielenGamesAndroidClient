package com.elpassion.vielengames.data.kuridor;

import java.util.List;

/**
 * Created by mateusz on 16/06/2014.
 */
public class MoveValidator {
    private static final int MAP_SIZE = 9;

    public static boolean validateMove(KuridorGameState kuridorGameState, KuridorMove kuridorMove) {
        return kuridorMove.getMoveType() == KuridorMove.MoveType.pawn ?
                validatePawnMove(kuridorGameState, kuridorMove) :
                validateWallMove(kuridorGameState, kuridorMove);
    }

    private static boolean validateWallMove(KuridorGameState kuridorGameState, KuridorMove kuridorMove) {
        return false;
    }

    private static boolean validatePawnMove(KuridorGameState kuridorGameState, KuridorMove kuridorMove) {
        int x = ((int) 'a') - ((int) kuridorMove.position.charAt(0));
        int y = ((int) '0') - ((int) kuridorMove.position.charAt(1));

        if (x < 0 || y < 0 || x >= MAP_SIZE || y >= MAP_SIZE)
            return false;

        if (blockingWall(kuridorGameState.getWalls(), x, y))
            return false;

        return false;
    }

    private static boolean blockingWall(List<WallPosition> walls, int x, int y)
    {
        for (WallPosition w : walls)
            if ()
    }
}
