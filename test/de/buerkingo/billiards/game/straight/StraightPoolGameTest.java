package de.buerkingo.billiards.game.straight;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

import de.buerkingo.billiards.participants.Person;

public class StraightPoolGameTest {

    private static final String PLAYER_A = "Player A";
    private static final String PLAYER_B = "Player B";

    private StraightPoolGame game;

    @Before
    public void before() {
        game = createGame();
    }

    @Test
    public void givenGameWhenEventIsProcessedThenControlPasses() {
        assertThat( game.getParticipants().getActiveParticipant().getIdentity().getName() )
            .isEqualTo( PLAYER_A );

        game.processEvent( new StraightPoolEvent() );

        assertThat( game.getParticipants().getActiveParticipant().getIdentity().getName() )
            .isEqualTo( PLAYER_B );
    }

    @Test
    public void givenGameWhenPlayerScoresNoPointsThenPointsAreUnchanged() {
        StraightPoolEvent event = new StraightPoolEvent()
            .withNumberOfBallsLeftInRack( StraightPoolRack.NUMBER_OF_BALLS )
            .withNumberOfFinishedRacks( 0 );

        game.processEvent( event );

        assertThat( game.getParticipants().get( 0 ).getPoints() ).isEqualTo( 0 );
        assertThat( game.getParticipants().get( 1 ).getPoints() ).isEqualTo( 0 );
    }

    private StraightPoolGame createGame() {
        StraightPoolGame game = new StraightPoolGame();
        game.getParticipants().setParticipants( createParticipant( PLAYER_A ), createParticipant( PLAYER_B ) );

        return game;
    }

    private StraightPoolParticipant createParticipant( String name ) {
        return new StraightPoolParticipant( new Person( name ) );
    }

}
