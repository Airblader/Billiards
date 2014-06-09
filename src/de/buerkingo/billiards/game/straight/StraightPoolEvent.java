package de.buerkingo.billiards.game.straight;

import java.io.Serializable;

import de.buerkingo.billiards.game.GameEvent;

public class StraightPoolEvent implements GameEvent, Serializable {

    private static final long serialVersionUID = 1L;

    private final int numberOfRemainingBalls;
    private boolean endedWithSafety = false;

    public StraightPoolEvent( int numberOfRemainingBalls ) {
        this.numberOfRemainingBalls = numberOfRemainingBalls;
    }

    public StraightPoolEvent withSafety() {
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