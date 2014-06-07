package de.buerkingo.billiards.participants;

import static com.google.common.collect.Lists.newArrayList;
import static java.lang.String.format;

import java.io.Serializable;
import java.util.List;

import de.buerkingo.billiards.util.reject.Reject;

/**
 * Holds and manages the list of participants in a game.
 */
public class Participants<T extends Participant> implements Serializable {

    private static final long serialVersionUID = 1L;

    private final List<T> participants = newArrayList();
    private int numberOfParticipants;
    private int activeParticipant;

    public T get( int participantIndex ) {
        Reject.ifEmpty( "participants have not yet been set", participants );
        Reject.ifLessThan( participantIndex, 0 );
        Reject.ifGreaterThan( format( "cannot get participant #%d, there are only %d participants", participantIndex, numberOfParticipants ),
            participantIndex, numberOfParticipants - 1 );

        return participants.get( participantIndex );
    }

    public T getActiveParticipant() {
        return get( activeParticipant );
    }

    public int getNumberOfParticipants() {
        return numberOfParticipants;
    }

    /** Triggers a turn, i.e. control will switch to the next player. */
    public void turn() {
        activeParticipant = ( activeParticipant == numberOfParticipants - 1 ) ? 0 : activeParticipant + 1;
    }

    public Participants<T> setParticipants( T... participants ) {
        Reject.ifNull( participants );
        Reject.ifNotEmpty( "can only set participants once", this.participants );

        numberOfParticipants = participants.length;
        for( T participant : participants ) {
            this.participants.add( participant );
        }

        return this;
    }

}