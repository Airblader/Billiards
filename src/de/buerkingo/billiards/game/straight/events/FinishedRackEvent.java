package de.buerkingo.billiards.game.straight.events;

import java.io.Serializable;

import de.buerkingo.billiards.game.GameEvent;
import de.buerkingo.billiards.util.reject.Reject;

/**
 * Event that should be sent if a player finishes the current rack.
 */
public class FinishedRackEvent implements GameEvent, Serializable {

    private static final long serialVersionUID = 1L;

    private final int numberOfRemainingBalls;

    public FinishedRackEvent( int numberOfRemainingBalls ) {
        Reject.ifLessThan( "a rack cannot be finished with less than zero remaining balls", numberOfRemainingBalls, 0 );
        Reject.ifGreaterThan( "a rack cannot be finished with more than one remaining ball", numberOfRemainingBalls, 1 );

        this.numberOfRemainingBalls = numberOfRemainingBalls;
    }

    public int getNumberOfRemainingBalls() {
        return numberOfRemainingBalls;
    }

}