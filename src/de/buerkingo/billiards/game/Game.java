package de.buerkingo.billiards.game;

import de.buerkingo.billiards.participants.Participant;
import de.buerkingo.billiards.participants.Participants;

/**
 * Represents an instance of a game.
 */
public interface Game<PARTICIPANT extends Participant, RACK extends Rack, STATE extends GameState> {

    /** Process events that logically belong together. */
    STATE processEvents( GameEvent... events );

    /** Get the rack. */
    RACK getRack();

    /** Get the participants. */
    Participants<PARTICIPANT> getParticipants();

}