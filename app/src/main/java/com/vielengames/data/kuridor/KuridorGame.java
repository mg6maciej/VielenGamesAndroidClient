package com.vielengames.data.kuridor;

import com.vielengames.data.Game;
import com.vielengames.data.Player;

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

    public Player getLoser() {
        if (winner == null) {
            return null;
        }
        for (Player potentialLoser : players) {
            if (!winner.equals(potentialLoser)) {
                return potentialLoser;
            }
        }
        throw new IllegalStateException();
    }
}
