package de.buerkingo.billiards.game;

import de.buerkingo.billiards.participants.Participants;

/**
 * Represents an instance of a game.
 */
public interface Game<EVENT extends GameEvent, RACK extends Rack> {

    /** Process the incoming event. */
    void processEvent( EVENT event );

    /** Get the rack. */
    RACK getRack();

    /** Get the participants. */
    Participants getParticipants();

}