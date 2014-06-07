package de.buerkingo.billiards.game.straight;

import static com.google.common.collect.Lists.newArrayList;

import java.io.Serializable;
import java.util.List;

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

    @Override
    public void processEvent( StraightPoolEvent event ) {
        Reject.ifNull( event );
        events.add( event );

        boolean isTurn = true;
        int scoredPoints = getEffectivelyScoredPoints( event );
        if( scoredPoints != 0 ) {
            participants.getActiveParticipant().addPoints( scoredPoints );
        }

        // TODO determine win

        if( isTurn ) {
            participants.turn();
        }
    }

    private int getEffectivelyScoredPoints( StraightPoolEvent event ) {
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