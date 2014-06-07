package de.buerkingo.billiards.game.straight;

import static com.google.common.collect.Lists.newArrayList;
import static de.buerkingo.billiards.game.straight.foul.SimpleFoul.NO_FOUL;

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

    private final StraightPoolRack rack = new StraightPoolRack();
    private final Participants<StraightPoolParticipant> participants = new Participants<StraightPoolParticipant>();
    private final List<StraightPoolEvent> events = newArrayList();

    // TODO should not be constant
    private final int pointsToWin = 100;

    @Override
    public boolean processEvent( StraightPoolEvent event ) {
        Reject.ifNull( event );
        events.add( event );

        boolean isTurn = true;
        int scoredPoints = getEffectivelyScoredPoints( event );
        if( scoredPoints != 0 ) {
            participants.getActiveParticipant().addPoints( scoredPoints );
        }

        if( activeParticipantHasWon() ) {
            return true;
        } else if( isTurn ) {
            participants.turn();
        }

        return false;
    }

    private boolean activeParticipantHasWon() {
        return participants.getActiveParticipant().getPoints() >= pointsToWin;
    }

    @VisibleForTesting
    protected int getEffectivelyScoredPoints( StraightPoolEvent event ) {
        return rack.getCurrentNumberOfBalls()
                + StraightPoolRack.NUMBER_OF_BALLS - event.getNumberOfBallsLeftInRack()
                + StraightPoolRack.NUMBER_OF_BALLS * ( event.getNumberOfFinishedRacks() - 1 )
                - event.getFoul().or( NO_FOUL ).getPointsToDeduct();
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