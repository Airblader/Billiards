package de.buerkingo.billiards.game;

import de.buerkingo.billiards.participants.Participant;
import de.buerkingo.billiards.participants.Participants;

/**
 * Represents an instance of a game.
 */
public interface Game<PARTICIPANT extends Participant, RACK extends Rack, STATE extends GameState> {

    /** Process the event. */
    STATE processEvent( GameEvent event );

    /** Get the rack. */
    RACK getRack();

    /** Get the participants. */
    Participants<PARTICIPANT> getParticipants();

}