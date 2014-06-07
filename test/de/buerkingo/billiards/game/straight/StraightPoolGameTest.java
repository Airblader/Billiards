package de.buerkingo.billiards.game.straight;

import static de.buerkingo.billiards.game.straight.StraightPoolRack.NUMBER_OF_BALLS;
import static org.fest.assertions.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;

import de.buerkingo.billiards.participants.Person;

@RunWith( DataProviderRunner.class )
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

    @Test
    @UseDataProvider( "provideEffectivelyScoredPoints" )
    public void testGetEffectivelyScoredPoints( int ballsLeftBefore, int ballsLeftAfter, int racks, int expected ) {
        game.getRack().setCurrentNumberOfBalls( ballsLeftBefore );

        StraightPoolEvent event = new StraightPoolEvent()
            .withNumberOfBallsLeftInRack( ballsLeftAfter )
            .withNumberOfFinishedRacks( racks );

        assertThat( game.getEffectivelyScoredPoints( event ) ).isEqualTo( expected );
    }

    @DataProvider
    public static Object[][] provideEffectivelyScoredPoints() {
        return new Object[][] {
            { NUMBER_OF_BALLS, NUMBER_OF_BALLS, 0, 0 },
            { NUMBER_OF_BALLS, 10, 0, 5 },
            { 10, 5, 0, 5 }
        };
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
