package de.buerkingo.billiards.game.straight;

import java.io.Serializable;
import java.util.List;

import com.google.common.collect.ImmutableList;

import de.buerkingo.billiards.game.GameState;
import de.buerkingo.billiards.util.reject.Reject;

public class StraightPoolState implements GameState, Serializable {

    private static final long serialVersionUID = 1L;

    private final List<StraightPoolParticipant> winners;

    public StraightPoolState( List<StraightPoolParticipant> winners ) {
        Reject.ifEmpty( winners );
        this.winners = ImmutableList.copyOf( winners );
    }

    /** Returns the winning participants in case the game is over. */
    public List<StraightPoolParticipant> getWinners() {
        return winners;
    }

}