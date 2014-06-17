package com.elpassion.vielengames.ui;

import com.elpassion.vielengames.data.kuridor.KuridorGame;
import com.elpassion.vielengames.data.kuridor.KuridorMove;

/**
 * Created by pawel on 17.06.14.
 */
public interface MoveRequestListener {
    void onRequest(KuridorGame game, KuridorMove move);
}
