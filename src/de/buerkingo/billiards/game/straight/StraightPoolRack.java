package de.buerkingo.billiards.game.straight;

import java.io.Serializable;

import de.buerkingo.billiards.game.Rack;

/**
 * Manages the rack for a straight pool game.
 */
public class StraightPoolRack implements Rack, Serializable {

    private static final long serialVersionUID = 1L;

    public static final int NUMBER_OF_BALLS = 15;

    private final int currentNumberOfBalls = NUMBER_OF_BALLS;

    /** Returns the number of balls that are currently still on the table. */
    public int getCurrentNumberOfBalls() {
        return currentNumberOfBalls;
    }

}