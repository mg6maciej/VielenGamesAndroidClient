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
public final class KuridorGame implements Game, android.os.Parcelable {

    String id;
    Player winner;
    List<Player> players;
    List<KuridorMove> moves;
    KuridorGameState currentState;

    public Player getActivePlayer() {
        String activeTeam = currentState.getActiveTeam();
        if (activeTeam == null) {
            return null;
        }
        for (Player p : players) {
            if (p.getTeam().equals(activeTeam)) {
                return p;
            }
        }
        throw new IllegalStateException();
    }
}
