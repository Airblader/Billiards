package de.buerkingo.billiards.game.straight;

import java.io.Serializable;

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

    @Override
    public void processEvent( StraightPoolEvent event ) {
        Reject.ifNull( event );

        boolean isTurn = true;
        if( event.getNumberOfFinishedRacks() != 0 || event.getNumberOfBallsLeftInRack() < rack.getCurrentNumberOfBalls() ) {
            // player has scored points
        }

        if( isTurn ) {
            participants.turn();
        }
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