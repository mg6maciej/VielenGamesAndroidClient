package com.elpassion.vielengames.data.kuridor;

import com.elpassion.vielengames.data.Game;
import com.elpassion.vielengames.data.Player;

import java.util.List;

import hrisey.Parcelable;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.experimental.Builder;

@Parcelable
@Value
@Builder
@EqualsAndHashCode(of = "id")
public final class KuridorGame implements Game {

    String id;
    Player winner;
    List<Player> players;
    List<KuridorMove> moves;
    KuridorGameState currentState;
}
