package de.buerkingo.billiards.game.straight;

import static com.google.common.collect.Lists.newArrayList;

import java.io.Serializable;
import java.util.List;

import com.google.common.annotations.VisibleForTesting;

import de.buerkingo.billiards.game.Game;
import de.buerkingo.billiards.participants.Participants;
import de.buerkingo.billiards.util.reject.Reject;

/**
 * Manages a game of straight pool.
 */
public class StraightPoolGame implements Game<StraightPoolEvent, StraightPoolRack, StraightPoolParticipant>, Serializable {

    private static final long serialVersionUID = 1L;

    private static final int MAX_CONSECUTIVE_FOULS = 3;

    private final StraightPoolRack rack = new StraightPoolRack();
    private final Participants<StraightPoolParticipant> participants = new Participants<StraightPoolParticipant>();

    private final List<StraightPoolEvent> events = newArrayList();

    // TODO should not be constant
    private final int pointsToWin = 100;

    @Override
    public boolean processEvent( StraightPoolEvent event ) {
        Reject.ifNull( event );
        events.add( event );

        // TODO should be false with more logic later
        boolean controlPasses = true;
        boolean requiresRerack = false;

        participants.getActiveParticipant().addPoints( getEffectivelyScoredPoints( event ) );
        if( activeParticipantHasWon() ) {
            return true;
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
                // TODO constant
                participants.getActiveParticipant().addPoints( -15 );
                participants.getActiveParticipant().resetConsecutiveFouls();

                requiresRerack = true;
            }
        } else {
            participants.getActiveParticipant().resetConsecutiveFouls();
        }

        if( controlPasses ) {
            participants.turn();
        }

        // TODO update rack
        return false;
    }

    private boolean hadAtLeastOneShotWithoutFoul( StraightPoolEvent event ) {
        return getScoredPoints( event ) > 0;
    }

    private boolean activeParticipantHasWon() {
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

}