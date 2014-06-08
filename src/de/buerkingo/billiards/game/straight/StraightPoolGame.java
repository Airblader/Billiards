package de.buerkingo.billiards.game.straight;

import java.io.Serializable;

import de.buerkingo.billiards.game.Game;
import de.buerkingo.billiards.participants.Participants;
import de.buerkingo.billiards.util.reject.Reject;

/**
 * Manages a game of straight pool.
 */
public class StraightPoolGame implements Game<StraightPoolEvent, StraightPoolRack, StraightPoolParticipant, StraightPoolState>, Serializable {

    private static final long serialVersionUID = 1L;

    private final int pointsToWin;
    private final StraightPoolRack rack = new StraightPoolRack();
    private final Participants<StraightPoolParticipant> participants = new Participants<StraightPoolParticipant>();

    private StraightPoolGame( int pointsToWin ) {
        this.pointsToWin = pointsToWin;
    }

    @Override
    public StraightPoolState processEvent( StraightPoolEvent event ) {
        Reject.ifNull( event );
        Reject.ifGreaterThan( "cannot have more balls on table than before",
            event.getNumberOfBallsLeftInRack(), rack.getCurrentNumberOfBalls() );

        return null;
    }

    @Override
    public StraightPoolRack getRack() {
        return rack;
    }

    @Override
    public Participants<StraightPoolParticipant> getParticipants() {
        return participants;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private int pointsToWin;

        public Builder withPointsToWin( int pointsToWin ) {
            this.pointsToWin = pointsToWin;
            return this;
        }

        public StraightPoolGame get() {
            return new StraightPoolGame( pointsToWin );
        }
    }

}