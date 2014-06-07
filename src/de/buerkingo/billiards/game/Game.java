package de.buerkingo.billiards.game;

import de.buerkingo.billiards.participants.Participant;
import de.buerkingo.billiards.participants.Participants;

/**
 * Represents an instance of a game.
 */
public interface Game<EVENT extends GameEvent, RACK extends Rack, PARTICIPANT extends Participant> {

    /**
     * Process the event.
     * This method returns {@code true} if and only if the game is over after this event.
     */
    boolean processEvent( EVENT event );

    /** Get the rack. */
    RACK getRack();

    /** Get the participants. */
    Participants<PARTICIPANT> getParticipants();

}