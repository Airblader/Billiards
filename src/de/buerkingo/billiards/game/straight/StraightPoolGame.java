package de.buerkingo.billiards.game.straight;

import java.io.Serializable;

import com.google.common.base.Optional;

import de.buerkingo.billiards.game.Game;
import de.buerkingo.billiards.game.GameEvent;
import de.buerkingo.billiards.game.straight.events.FinishedInningEvent;
import de.buerkingo.billiards.game.straight.events.FinishedRackEvent;
import de.buerkingo.billiards.game.straight.foul.Foul;
import de.buerkingo.billiards.participants.Participants;
import de.buerkingo.billiards.util.reject.Reject;

/**
 * Manages a game of straight pool.
 */
public class StraightPoolGame implements Game<StraightPoolParticipant, StraightPoolRack, StraightPoolState>, Serializable {

    private static final long serialVersionUID = 1L;

    private final int pointsToWin;
    private final Optional<Integer> maxInnings;

    private final StraightPoolRack rack = new StraightPoolRack();
    private final Participants<StraightPoolParticipant> participants = new Participants<StraightPoolParticipant>();

    private StraightPoolGame( int pointsToWin, Optional<Integer> maxInnings ) {
        this.pointsToWin = pointsToWin;
        this.maxInnings = maxInnings;
    }

    public StraightPoolState processEvents( GameEvent event, Optional<Foul> foul ) {
        Reject.ifNull( event );
        Reject.ifNull( foul );

        StraightPoolParticipant participant = participants.getActiveParticipant();

        boolean controlPasses = false;

        if( event instanceof FinishedInningEvent ) {
            FinishedInningEvent inningEvent = (FinishedInningEvent) event;
            Reject.ifGreaterThan( "there cannot be more balls left than before",
                inningEvent.getNumberOfRemainingBalls(), rack.getCurrentNumberOfBalls() );

            int pocketedBalls = rack.getCurrentNumberOfBalls() - inningEvent.getNumberOfRemainingBalls();
            if( pocketedBalls > 0 ) {
                participant.getInning().addPoints( pocketedBalls );
                participant.resetConsecutiveFouls();
            }

            if( inningEvent.endedWithSafety() ) {
                participant.getInning().endedWithSafety();
                controlPasses = true;
            }

            // TODO refine logic
            if( inningEvent.getNumberOfRemainingBalls() > 1 ) {
                controlPasses = true;
            }

            // TODO advance inning
        } else if( event instanceof FinishedRackEvent ) {
            FinishedRackEvent rackEvent = (FinishedRackEvent) event;

            // TODO handle event
        } else {
            Reject.always( "unknown event type" );
        }

        if( foul.isPresent() ) {
            controlPasses = true;
        }

        if( controlPasses ) {
            participants.turn();
        }

        // TODO return state
        return null;
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
        private Optional<Integer> maxInnings = Optional.absent();

        public Builder withPointsToWin( int pointsToWin ) {
            this.pointsToWin = pointsToWin;
            return this;
        }

        public Builder withMaxInnings( int maxInnings ) {
            Reject.ifEqual( "must have a positive number of innings", maxInnings, 0 );

            this.maxInnings = Optional.of( maxInnings );
            return this;
        }

        public StraightPoolGame get() {
            Reject.ifEqual( "points needed to win need to be set", pointsToWin, 0 );

            return new StraightPoolGame( pointsToWin, maxInnings );
        }
    }

}