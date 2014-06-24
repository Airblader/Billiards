package de.buerkingo.billiards.game.straight;

import java.io.Serializable;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;

import de.buerkingo.billiards.game.GameState;
import de.buerkingo.billiards.util.reject.Reject;

public class StraightPoolWinner implements GameState, Serializable {

    private static final long serialVersionUID = 1L;

    private final List<StraightPoolParticipant> winners;

    public StraightPoolWinner( List<StraightPoolParticipant> winners ) {
        Reject.ifEmpty( winners );
        this.winners = ImmutableList.copyOf( winners );
    }

    /** Returns the winner if there was only one winner */
    public StraightPoolParticipant getWinner() {
        Reject.ifGreaterThan( "there was no winner", winners.size(), 1 );
        return Iterables.getOnlyElement( winners );
    }

    /** Returns {@code true} if and only if the game ended in a draw. */
    public boolean hasDrawed() {
        return winners.size() == 2;
    }

}