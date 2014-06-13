package de.buerkingo.billiards.game.straight;

import static com.google.common.collect.Iterables.filter;
import static de.buerkingo.billiards.game.straight.StraightPoolRack.NUMBER_OF_BALLS;

import java.io.Serializable;
import java.util.List;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;

import de.buerkingo.billiards.game.Game;
import de.buerkingo.billiards.game.straight.foul.ConsecutiveFoulsFoul;
import de.buerkingo.billiards.game.straight.foul.Foul;
import de.buerkingo.billiards.participants.Participants;
import de.buerkingo.billiards.util.BooleanSwitch.OnSwitch;
import de.buerkingo.billiards.util.reject.Reject;

/**
 * Manages a game of straight pool.
 */
public class StraightPoolGame implements Game<StraightPoolParticipant, StraightPoolRack, StraightPoolState>, Serializable {

    private static final long serialVersionUID = 1L;

    private static final int MAX_CONSECUTIVE_FOULS = 3;

    private final int pointsToWin;
    private final Optional<InningsLimit> inningsLimit;

    private final StraightPoolRack rack = new StraightPoolRack();
    private final Participants<StraightPoolParticipant> participants = new Participants<StraightPoolParticipant>();

    private OnSwitch gameOver = OnSwitch.off();

    private StraightPoolGame( int pointsToWin, Optional<InningsLimit> inningsLimit ) {
        this.pointsToWin = pointsToWin;
        this.inningsLimit = inningsLimit;
    }

    public StraightPoolState processEvents( StraightPoolEvent event, Optional<? extends Foul> foul ) {
        Reject.ifNull( event );
        Reject.ifNull( foul );
        Reject.ifGreaterThan( "there cannot be more balls left than before",
            event.getNumberOfRemainingBalls(), rack.getCurrentNumberOfBalls() );
        Reject.ifTrue( "game is already over", gameOver.get() );

        StraightPoolParticipant participant = participants.getActiveParticipant();

        OnSwitch controlPasses = OnSwitch.off();
        OnSwitch requiresRerack = OnSwitch.off();

        int pocketedBalls = rack.getCurrentNumberOfBalls() - event.getNumberOfRemainingBalls();
        if( pocketedBalls > 0 ) {
            participant.getInningOrNew().addPoints( pocketedBalls );
            participant.resetConsecutiveFouls();
        }

        if( event.endedWithSafety() ) {
            participant.getInningOrNew().endedWithSafety();
            controlPasses.on();
        }

        switch( event.getNumberOfRemainingBalls() ) {
            case 0:
            case 1:
                requiresRerack.on();
                break;
            default:
                if( !requiresRerack.get() ) {
                    controlPasses.on();
                }
        }

        if( foul.isPresent() ) {
            controlPasses.on();

            participant.getInningOrNew().addFoul( foul.get() );

            if( foul.get().requiresRerack() ) {
                requiresRerack.on();
            }

            if( foul.get().getReason().countsAsFoul() ) {
                participant.increaseConsecutiveFouls();
            } else {
                participant.resetConsecutiveFouls();
            }

            if( participant.getConsecutiveFouls() == MAX_CONSECUTIVE_FOULS ) {
                participant.getInningOrNew().addFoul( new ConsecutiveFoulsFoul() );
                participant.resetConsecutiveFouls();

                requiresRerack.on();
            }
        } else {
            participant.resetConsecutiveFouls();
        }

        if( controlPasses.get() ) {
            participant.getInningOrNew().end();
        }

        Optional<StraightPoolParticipant> winner = getWinner();
        if( winner.isPresent() ) {
            gameOver.on();
            return new StraightPoolState( winner.get() );
        }

        if( controlPasses.get() ) {
            participants.turn();
        }

        rack.setCurrentNumberOfBalls( requiresRerack.get() ? NUMBER_OF_BALLS : event.getNumberOfRemainingBalls() );
        return new StraightPoolState( participant, requiresRerack.get() );
    }

    private Optional<StraightPoolParticipant> getWinner() {
        return getWinnerByPoints().or( getWinnerByInnings() );
    }

    private Optional<StraightPoolParticipant> getWinnerByPoints() {
        ImmutableList<StraightPoolParticipant> winner = ImmutableList.copyOf( filter( participants.getParticipants(), new Predicate<StraightPoolParticipant>() {
            @Override
            public boolean apply( StraightPoolParticipant participant ) {
                return participant.getLastInning().hasEnded() && participant.getPoints() >= pointsToWin;
            }
        } ) );

        Reject.ifGreaterThan( "two players cannot win at the same time", winner.size(), 1 );
        return Optional.fromNullable( Iterables.getFirst( winner, null ) );
    }

    private Optional<StraightPoolParticipant> getWinnerByInnings() {
        if( !inningsLimit.isPresent() ) {
            return Optional.absent();
        }

        for( int i = 0; i < participants.getNumberOfParticipants(); i++ ) {
            StraightPoolInning inning = participants.get( i ).getLastInning();
            if( inning == null || inning.getNumber() < inningsLimit.get().getMaxInnings() || !inning.hasEnded() ) {
                return Optional.absent();
            }
        }

        List<StraightPoolParticipant> topTwo = StraightPoolParticipant.BY_POINTS.greatestOf( participants.getParticipants(), 2 );
        return ( topTwo.get( 0 ).getPoints() == topTwo.get( 1 ).getPoints() )
                ? Optional.<StraightPoolParticipant>absent() : Optional.of( topTwo.get( 0 ) );
    }

    @Override
    public Participants<StraightPoolParticipant> getParticipants() {
        return participants;
    }

    @VisibleForTesting
    protected StraightPoolRack getRack() {
        return rack;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private int pointsToWin;
        private Optional<InningsLimit> inningsLimit = Optional.absent();

        public Builder withPointsToWin( int pointsToWin ) {
            this.pointsToWin = pointsToWin;
            return this;
        }

        public Builder withMaxInnings( int maxInnings ) {
            return withInningsLimit( maxInnings, 1 );
        }

        public Builder withInningsLimit( int maxInnings, int extension ) {
            Reject.ifEqual( "must have a positive number of innings", maxInnings, 0 );

            this.inningsLimit = Optional.of( new InningsLimit( maxInnings, extension ) );
            return this;
        }

        public StraightPoolGame get() {
            Reject.ifEqual( "points needed to win need to be set", pointsToWin, 0 );

            return new StraightPoolGame( pointsToWin, inningsLimit );
        }
    }

}