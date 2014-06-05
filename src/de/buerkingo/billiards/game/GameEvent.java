package de.buerkingo.billiards.game;

/**
 * Represents an instance of a game event.
 */
public interface GameEvent {

    /** Specifies whether or not this event causes the player at the table to switch to the next player. */
    boolean isTurn();

}