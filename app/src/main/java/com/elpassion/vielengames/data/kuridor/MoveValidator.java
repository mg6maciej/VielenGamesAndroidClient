package com.elpassion.vielengames.data.kuridor;

/**
 * Created by mateusz on 16/06/2014.
 */
public class MoveValidator {
    public static boolean validateMove(KuridorGameState kuridorGameState, KuridorMove kuridorMove) {
        return kuridorMove.getMoveType() == KuridorMove.MoveType.pawn ?
                validatePawnMove(kuridorGameState, kuridorMove) :
                validateWallMove(kuridorGameState, kuridorMove);
    }

    private static boolean validateWallMove(KuridorGameState kuridorGameState, KuridorMove kuridorMove) {
        //int x = ((int) 'a') - kuridorMove.position[0];
        return false;
    }

    private static boolean validatePawnMove(KuridorGameState kuridorGameState, KuridorMove kuridorMove) {
        return false;
    }
}
