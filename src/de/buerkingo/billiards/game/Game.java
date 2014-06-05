package de.buerkingo.billiards.game;

/**
 * Represents an instance of a game.
 */
public interface Game<EVENT extends GameEvent, RACK extends Rack> {

    /** Process the incoming event. */
    void processEvent( EVENT event );

    RACK getRack();

}