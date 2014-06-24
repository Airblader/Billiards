package de.buerkingo.billiards.game.straight;

import static com.google.common.collect.Iterables.filter;
import static de.buerkingo.billiards.game.straight.StraightPoolRack.NUMBER_OF_BALLS;

import java.io.Serializable;
import java.util.List;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableList;

import de.buerkingo.billiards.game.Game;
import de.buerkingo.billiards.game.straight.foul.ConsecutiveFoulsFoul;
import de.buerkingo.billiards.game.straight.foul.Foul;
import de.buerkingo.billiards.participants.Participants;
import de.buerkingo.billiards.util.BooleanSwitch.OnSwitch;
import de.buerkingo.billiards.util.reject.Reject;

/**
 * Manages a game of straight pool.
 */
public class StraightPoolGame implements Game<StraightPoolParticipant, StraightPoolRack, StraightPoolWinner>, Serializable {

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

    public Optional<StraightPoolWinner> processEvents( StraightPoolEvent event, Optional<? extends Foul> foul ) {
        Reject.ifNull( event );
        Reject.ifNull( foul );
        Reject.ifGreaterThan( "there cannot be more balls left than before",
            event.getNumberOfRemainingBalls(), rack.getCurrentNumberOfBalls() );
        Reject.ifTrue( "game is already over", gameOver.get() );

        StraightPoolParticipant participant = participants.getActiveParticipant();

        OnSwitch controlPasses = OnSwitch.off();
        OnSwitch requiresRerack = OnSwitch.off();

        int pointsBefore = participant.getPoints();
        int pocketedBalls = rack.getCurrentNumberOfBalls() - event.getNumberOfRemainingBalls();
        if( pocketedBalls > 0 ) {
            int cappedPoints = Math.min( pocketedBalls, pointsToWin - pointsBefore );

            participant.getInningOrNew().addPoints( cappedPoints );
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
                controlPasses.on();
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

        if( gameHasEnded() ) {
            gameOver.on();
            return Optional.of( new StraightPoolWinner( getLeadersByScore() ) );
        }

        if( controlPasses.get() ) {
            participants.turn();
        }

        rack.setCurrentNumberOfBalls( requiresRerack.get() ? NUMBER_OF_BALLS : event.getNumberOfRemainingBalls() );
        return Optional.absent();
    }

    private List<StraightPoolParticipant> getLeadersByScore() {
        int score = 0;
        for( StraightPoolParticipant participant : participants.getParticipants() ) {
            score = Math.max( score, participant.getPoints() );
        }

        final int highestScore = score;
        return ImmutableList.copyOf( filter( participants.getParticipants(), new Predicate<StraightPoolParticipant>() {
            @Override
            public boolean apply( StraightPoolParticipant participant ) {
                return participant.getPoints() >= highestScore;
            }
        } ) );
    }

    private boolean gameHasEnded() {
        return gameHasEndedDueToPointsLimit() || gameHasEndedDueToInningsLimit();
    }

    private boolean gameHasEndedDueToPointsLimit() {
        ImmutableList<StraightPoolParticipant> winner = ImmutableList.copyOf( filter( participants.getParticipants(), new Predicate<StraightPoolParticipant>() {
            @Override
            public boolean apply( StraightPoolParticipant participant ) {
                return participant.getLastInning().hasEnded() && participant.getPoints() >= pointsToWin;
            }
        } ) );

        return !winner.isEmpty();
    }

    private boolean gameHasEndedDueToInningsLimit() {
        if( !inningsLimit.isPresent() ) {
            return false;
        }

        Optional<Integer> extension = inningsLimit.get().getExtension();
        for( int i = 0; i < participants.getNumberOfParticipants(); i++ ) {
            StraightPoolInning inning = participants.get( i ).getLastInning();
            if( inning == null || !inning.hasEnded() ) {
                return false;
            }

            if( inning.getNumber() < inningsLimit.get().getMaxInnings() ) {
                return false;
            }

            if( extension.isPresent() && ( inning.getNumber() - inningsLimit.get().getMaxInnings() ) % extension.get() != 0 ) {
                return false;
            }
        }

        List<StraightPoolParticipant> byPoints = StraightPoolParticipant.BY_POINTS.greatestOf( participants.getParticipants(), 2 );
        return !extension.isPresent() || byPoints.get( 0 ).getPoints() != byPoints.get( 1 ).getPoints();
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

        public Builder withInningsLimit( int maxInnings, Optional<Integer> extension ) {
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