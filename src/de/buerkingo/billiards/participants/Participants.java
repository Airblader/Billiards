package de.buerkingo.billiards.participants;

import static com.google.common.collect.Lists.newArrayList;
import static java.lang.String.format;

import java.io.Serializable;
import java.util.List;

import de.buerkingo.billiards.util.reject.Reject;

/**
 * Holds and manages the list of participants in a game.
 */
public class Participants implements Serializable {

    private static final long serialVersionUID = 1L;

    private final int numberOfParticipants = 2;
    private final List<Participant> participants = newArrayList();

    public Participant get( int participantIndex ) {
        Reject.ifLessThan( participantIndex, 0 );
        Reject.ifGreaterThan( format( "cannot get participant #%d, there are only %d participants", participantIndex, numberOfParticipants ),
            participantIndex, numberOfParticipants - 1 );

        return participants.get( participantIndex );
    }
}