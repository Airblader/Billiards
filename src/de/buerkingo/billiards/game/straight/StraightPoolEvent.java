package de.buerkingo.billiards.game.straight;

import java.io.Serializable;

import de.buerkingo.billiards.game.GameEvent;
import de.buerkingo.billiards.util.reject.Reject;

/**
 * Represents one set of information to be handled by the game logic.
 */
public class StraightPoolEvent implements GameEvent, Serializable {

    private static final long serialVersionUID = 1L;

    private final boolean isTurn = true;
    private int numberOfBallsLeftInRack;
    private int numberOfFinishedRacks;

    /** The number of balls that are left on the table after this event. */
    public StraightPoolEvent withNumberOfBallsLeftInRack( int numberOfBallsLeftInRack ) {
        Reject.ifLessThan( numberOfBallsLeftInRack, 0 );
        Reject.ifGreaterThan( numberOfBallsLeftInRack, StraightPoolRack.NUMBER_OF_BALLS );

        this.numberOfBallsLeftInRack = numberOfBallsLeftInRack;
        return this;
    }

    /** The number of racks that were finished during this event. This equals the number of re-racks that took place. */
    public StraightPoolEvent withNumberOfFinishedRacks( int numberOfFinishedRacks ) {
        Reject.ifLessThan( numberOfFinishedRacks, 0 );

        this.numberOfFinishedRacks = numberOfFinishedRacks;
        return this;
    }

    @Override
    public boolean isTurn() {
        return isTurn;
    }

    public int getNumberOfBallsLeftInRack() {
        return numberOfBallsLeftInRack;
    }

    public int getNumberOfFinishedRacks() {
        return numberOfFinishedRacks;
    }

}