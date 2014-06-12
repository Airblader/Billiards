package de.buerkingo.billiards.game.straight;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.common.base.Optional;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;

import de.buerkingo.billiards.game.straight.foul.Foul;
import de.buerkingo.billiards.util.DataProviders;
import de.buerkingo.billiards.util.reject.AssumptionException;

@RunWith( DataProviderRunner.class )
public class StraightPoolGameTest extends StraightPoolGameTestBase {

    @Before
    public void before() {
        game = createGame( 60, Optional.<Integer>absent() );
    }

    @Test
    public void givenEventWhenProcessedThenInningAdvances() {
        assertThat( getParticipant( PLAYER_A ).getInningOrNew().getNumber() ).isEqualTo( 1 );

        game.processEvents( new StraightPoolEvent( 15 ), Optional.<Foul>absent() );

        assertThat( getParticipant( PLAYER_A ).getInningOrNew().getNumber() ).isEqualTo( 2 );
    }

    @Test
    public void givenEventWithMoreBallsThanOnTableWhenProcessedThenRejected() {
        game.processEvents( new StraightPoolEvent( 10 ), Optional.<Foul>absent() );

        thrown.expect( AssumptionException.class );
        game.processEvents( new StraightPoolEvent( 13 ), Optional.<Foul>absent() );
    }

    @Test
    @UseDataProvider( value = "provideZeroToFifteen", location = DataProviders.class )
    public void givenEventWhenProcessedThenRackIsUpdates( int ballsLeft ) {
        game.processEvents( new StraightPoolEvent( ballsLeft ), Optional.<Foul>absent() );
        assertThat( game.getRack().getCurrentNumberOfBalls() ).isEqualTo( ballsLeft < 2 ? 15 : ballsLeft );
    }

}