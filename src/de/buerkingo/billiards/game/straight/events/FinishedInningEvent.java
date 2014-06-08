package de.buerkingo.billiards.game.straight.events;

import java.io.Serializable;

import de.buerkingo.billiards.game.GameEvent;

/**
 * Event that should be sent when a player finishes his inning.
 */
public class FinishedInningEvent implements GameEvent, Serializable {

    private static final long serialVersionUID = 1L;

    private final int numberOfRemainingBalls;
    private boolean endedWithSafety = false;

    public FinishedInningEvent( int numberOfRemainingBalls ) {
        this.numberOfRemainingBalls = numberOfRemainingBalls;
    }

    public FinishedInningEvent withSafety() {
        this.endedWithSafety = true;
        return this;
    }

    public int getNumberOfRemainingBalls() {
        return numberOfRemainingBalls;
    }

    public boolean endedWithSafety() {
        return endedWithSafety;
    }

}