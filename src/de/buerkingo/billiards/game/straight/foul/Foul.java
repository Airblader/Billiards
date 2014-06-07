package de.buerkingo.billiards.game.straight.foul;

/**
 * Interface for a foul.
 */
public interface Foul {

    /** The number of points to deduct for the foul. */
    int getPointsToDeduct();

    /** Determines whether this foul requires a re-rack. */
    boolean doesRequireRerack();

    /** Determines whether this counts as a foul (e.g. for the consecutive foul rules). */
    boolean countsAsFoul();

}
