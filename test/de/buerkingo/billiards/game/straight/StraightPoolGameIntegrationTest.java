package de.buerkingo.billiards.game.straight;

import org.junit.Test;

public class StraightPoolGameIntegrationTest extends StraightPoolGameTestBase {

    @Test
    public void testGame() {
        eventWithBallsLeft( 10 );
        assertThatParticipantHasPoints( PLAYER_A, 5 );

        eventWithBallsLeft( 5 );
        assertThatParticipantHasPoints( PLAYER_B, 5 );

        eventWithBallsLeft( 0 );
        assertThatParticipantHasPoints( PLAYER_A, 10 );

        eventWithBallsLeft( 10 );
        assertThatParticipantHasPoints( PLAYER_A, 15 );

        eventWithBallsLeft( 1 );
        assertThatParticipantHasPoints( PLAYER_B, 14 );

        eventWithBallsLeft( 13 );
        assertThatParticipantHasPoints( PLAYER_B, 17 );

        eventWithBallsLeft( 8 );
        assertThatParticipantHasPoints( PLAYER_A, 20 );

        eventWithBallsLeft( 5 );
        assertThatParticipantHasPoints( PLAYER_B, 20 );

        // TODO win
    }

    private void eventWithBallsLeft( int ballsLeft ) {
        game.processEvent( new StraightPoolEvent()
            .withNumberOfBallsLeftInRack( ballsLeft ) );
    }

}
