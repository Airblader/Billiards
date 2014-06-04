package de.buerkingo.billiards.game.straight;

import java.io.Serializable;

import de.buerkingo.billiards.game.GameEvent;

/**
 * Represents one set of information to be handled by the game logic.
 */
public class StraightPoolGameEvent implements GameEvent, Serializable {

    private static final long serialVersionUID = 1L;

    private int numberOfBallsLeftInRack;
    private int numberOfFinishedRacks;

    private StraightPoolGameEvent() {}

    /** The number of balls that are left on the table after this event. */
    public StraightPoolGameEvent withNumberOfBallsLeftInRack( int numberOfBallsLeftInRack ) {
        this.numberOfBallsLeftInRack = numberOfBallsLeftInRack;
        return this;
    }

    /** The number of racks that were finished during this event. This equals the number of re-racks that took place. */
    public StraightPoolGameEvent withNumberOfFinishedRacks( int numberOfFinishedRacks ) {
        this.numberOfFinishedRacks = numberOfFinishedRacks;
        return this;
    }

    public int getNumberOfBallsLeftInRack() {
        return numberOfBallsLeftInRack;
    }

    public int getNumberOfFinishedRacks() {
        return numberOfFinishedRacks;
    }

}