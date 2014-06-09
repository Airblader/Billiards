package de.buerkingo.billiards.game.straight;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;

import de.buerkingo.billiards.participants.Participants;
import de.buerkingo.billiards.participants.Person;

public class StraightPoolParticipantTest {

    private static final String PLAYER_A = "Player A";
    private static final String PLAYER_B = "Player B";

    @Test
    public void testGetInningIdempotency() {
        Participants<StraightPoolParticipant> participants = new Participants<StraightPoolParticipant>()
            .setParticipants( createParticipant( PLAYER_A ), createParticipant( PLAYER_B ) );

        assertThat( participants.getActiveParticipant().getInningOrNew().getNumber() ).isEqualTo( 1 );
        assertThat( participants.getActiveParticipant().getInningOrNew().getNumber() ).isEqualTo( 1 );

        participants.getActiveParticipant().getInningOrNew().end();

        assertThat( participants.getActiveParticipant().getInningOrNew().getNumber() ).isEqualTo( 2 );
        assertThat( participants.getActiveParticipant().getInningOrNew().getNumber() ).isEqualTo( 2 );
    }

    private StraightPoolParticipant createParticipant( String name ) {
        return new StraightPoolParticipant( new Person( name ) );

    }

}