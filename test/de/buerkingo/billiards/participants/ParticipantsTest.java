package de.buerkingo.billiards.participants;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;

import de.buerkingo.billiards.game.straight.StraightPoolParticipant;

public class ParticipantsTest {

    private static final String PLAYER_A = "Player A";
    private static final String PLAYER_B = "Player B";

    @Test
    public void testTurn() {
        Participants<StraightPoolParticipant> participants = new Participants<StraightPoolParticipant>()
            .setParticipants( createParticipant( PLAYER_A ), createParticipant( PLAYER_B ) );
        assertThat( participants.getActiveParticipant().getIdentity().getName() )
            .isEqualTo( PLAYER_A );

        participants.turn();
        assertThat( participants.getActiveParticipant().getIdentity().getName() )
            .isEqualTo( PLAYER_B );

        participants.turn();
        assertThat( participants.getActiveParticipant().getIdentity().getName() )
            .isEqualTo( PLAYER_A );
    }

    private StraightPoolParticipant createParticipant( String name ) {
        return new StraightPoolParticipant( new Person( name ) );
    }

}