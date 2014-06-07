package de.buerkingo.billiards.game.straight;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;

@RunWith( DataProviderRunner.class )
public class StraightPoolGameTest extends StraightPoolGameTestBase {

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
            .withNumberOfBallsLeftInRack( StraightPoolRack.NUMBER_OF_BALLS );

        game.processEvent( event );

        assertThat( game.getParticipants().get( 0 ).getPoints() ).isEqualTo( 0 );
        assertThat( game.getParticipants().get( 1 ).getPoints() ).isEqualTo( 0 );
    }

    @Test
    @UseDataProvider( "provideEffectivelyScoredPoints" )
    public void testGetEffectivelyScoredPoints( int ballsLeftBefore, int ballsLeftAfter, int expected ) {
        game.getRack().setCurrentNumberOfBalls( ballsLeftBefore );

        StraightPoolEvent event = new StraightPoolEvent()
            .withNumberOfBallsLeftInRack( ballsLeftAfter );

        assertThat( game.getEffectivelyScoredPoints( event ) ).isEqualTo( expected );
    }

    @DataProvider
    public static Object[][] provideEffectivelyScoredPoints() {
        return new Object[][] {
            { 15, 15, 0 },
            { 15, 10, 5 },
            { 10, 5, 5 }
        };
    }

}
