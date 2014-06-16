package com.elpassion.vielengames.data.kuridor;

import com.elpassion.vielengames.data.Game;
import com.elpassion.vielengames.data.Player;

import java.util.List;

import hrisey.Parcelable;
import lombok.Value;

@Parcelable
@Value
public final class KuridorGame implements Game {

    String id;
    Player winner;
    Player activePlayer;
    List<Player> players;
    List<KuridorMove> moves;
    KuridorGameState currentState;
}
