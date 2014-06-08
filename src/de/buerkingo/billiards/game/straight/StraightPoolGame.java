package de.buerkingo.billiards.game.straight;

import java.io.Serializable;

import com.google.common.annotations.VisibleForTesting;

import de.buerkingo.billiards.game.Game;
import de.buerkingo.billiards.participants.Participants;
import de.buerkingo.billiards.util.reject.Reject;

/**
 * Manages a game of straight pool.
 */
public class StraightPoolGame implements Game<StraightPoolEvent, StraightPoolRack, StraightPoolParticipant, StraightPoolState>, Serializable {

    private static final long serialVersionUID = 1L;

    private static final int MAX_CONSECUTIVE_FOULS = 3;
    private static final int CONSECUTIVE_FOUL_PENALTY = -15;

    private final int pointsToWin;
    private final StraightPoolRack rack = new StraightPoolRack();
    private final Participants<StraightPoolParticipant> participants = new Participants<StraightPoolParticipant>();

    private StraightPoolGame( int pointsToWin ) {
        this.pointsToWin = pointsToWin;
    }

    @Override
    public StraightPoolState processEvent( StraightPoolEvent event ) {
        Reject.ifNull( event );

        boolean controlPasses = false;
        boolean requiresRerack = false;

        participants.getActiveParticipant().addPoints( getEffectivelyScoredPoints( event ) );
        if( activeParticipantHasWon() ) {
            return new StraightPoolState( participants.getActiveParticipant() );
        }

        if( hadAtLeastOneShotWithoutFoul( event ) ) {
            participants.getActiveParticipant().resetConsecutiveFouls();
        }

        if( event.getFoul().isPresent() ) {
            controlPasses = true;

            if( event.getFoul().get().requiresRerack() ) {
                requiresRerack = true;
            }

            if( event.getFoul().get().countsAsFoul() ) {
                participants.getActiveParticipant().increaseConsecutiveFouls();
            }

            if( participants.getActiveParticipant().getConsecutiveFouls() == MAX_CONSECUTIVE_FOULS ) {
                participants.getActiveParticipant().addPoints( CONSECUTIVE_FOUL_PENALTY );
                participants.getActiveParticipant().resetConsecutiveFouls();

                requiresRerack = true;
            }
        } else {
            participants.getActiveParticipant().resetConsecutiveFouls();
        }

        // TODO process safeties

        switch( event.getNumberOfBallsLeftInRack() ) {
            case 0:
            case 1:
                break;
            default:
                if( !requiresRerack ) {
                    controlPasses = true;
                }

                break;
        }

        controlPasses &= !requiresRerack;
        if( controlPasses ) {
            participants.turn();
        }

        // TODO update rack
        return new StraightPoolState( participants.getActiveParticipant(), requiresRerack );
    }

    private boolean hadAtLeastOneShotWithoutFoul( StraightPoolEvent event ) {
        return getScoredPoints( event ) > 0;
    }

    private boolean activeParticipantHasWon() {
        // TODO win by innings
        return participants.getActiveParticipant().getPoints() >= pointsToWin;
    }

    @VisibleForTesting
    protected int getEffectivelyScoredPoints( StraightPoolEvent event ) {
        int foulPoints = event.getFoul().isPresent() ? event.getFoul().get().getPointsToDeduct() : 0;
        return getScoredPoints( event ) - foulPoints;
    }

    private int getScoredPoints( StraightPoolEvent event ) {
        return rack.getCurrentNumberOfBalls() - event.getNumberOfBallsLeftInRack();
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