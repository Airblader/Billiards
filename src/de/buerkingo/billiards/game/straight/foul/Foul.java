package de.buerkingo.billiards.game.straight.foul;

/**
 * Interface for a foul.
 */
public interface Foul {

    /** The number of points to deduct for the foul. */
    int getPointsToDeduct();

    /** Determines whether this foul requires a re-rack. */
    boolean doesRequireRerack();

}
