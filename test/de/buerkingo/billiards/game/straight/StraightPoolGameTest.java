package de.buerkingo.billiards.game.straight;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;

import de.buerkingo.billiards.util.DataProviders;

@RunWith( DataProviderRunner.class )
public class StraightPoolGameTest extends StraightPoolGameTestBase {

    @Test
    public void givenGameWhenEventIsProcessedThenControlPasses() {
        assertThat( game.getParticipants().getActiveParticipant().getIdentity().getName() )
            .isEqualTo( PLAYER_A );

        game.processEvent( new StraightPoolEvent()
            .withNumberOfBallsLeftInRack( 15 ) );

        assertThat( game.getParticipants().getActiveParticipant().getIdentity().getName() )
            .isEqualTo( PLAYER_B );
    }

    @Test
    public void givenGameWhenPlayerScoresNoPointsThenPointsAreUnchanged() {
        game.processEvent( new StraightPoolEvent()
            .withNumberOfBallsLeftInRack( StraightPoolRack.NUMBER_OF_BALLS ) );

        assertThat( getParticipant( PLAYER_A ).getPoints() ).isEqualTo( 0 );
        assertThat( getParticipant( PLAYER_B ).getPoints() ).isEqualTo( 0 );
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

    @Test
    public void givenNewGameWhenNothingHappenedThenItIsTheFirstShot() {
        assertThat( game.isFirstShotAfterRerack() ).isTrue();
    }

    @Test
    public void givenGameWhenOneParticipantPlayedThenItIsNotTheFirstShot() {
        game.processEvent( new StraightPoolEvent()
            .withNumberOfBallsLeftInRack( StraightPoolRack.NUMBER_OF_BALLS ) );

        assertThat( game.isFirstShotAfterRerack() ).isFalse();
    }

    @Test
    @UseDataProvider( value = "provideZeroOne", location = DataProviders.class )
    public void givenGameWhenRackWasRunOutThenItIsNotTheFirstShot( int ballsLeft ) {
        game.processEvent( new StraightPoolEvent()
            .withNumberOfBallsLeftInRack( ballsLeft ) );

        assertThat( game.isFirstShotAfterRerack() ).isFalse();
    }

    @Test
    public void givenGameWhenParticipantReachesPointsLimitThenParticipantWins() {
        createGame( 5 );

        StraightPoolState state = game.processEvent( new StraightPoolEvent()
            .withNumberOfBallsLeftInRack( 10 ) );

        assertThat( state.isGameOver() ).isTrue();
        assertThat( state.getWinner() ).isEqualTo( getParticipant( PLAYER_A ) );
    }

}
