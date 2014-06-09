package de.buerkingo.billiards.game.straight;

import static org.fest.assertions.Assertions.assertThat;

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

    @Test
    public void givenEventWithScoredPointsWhenProcessedThenInningIsProcessedCorrectly() {
        game.processEvents( new StraightPoolEvent( 10 ), Optional.<Foul>absent() );

        StraightPoolParticipant participant = getParticipant( PLAYER_A );
        assertThat( participant.getConsecutiveFouls() ).isEqualTo( 0 );
        assertThat( participant.getInning().getNumber() ).isEqualTo( 2 );

        assertThat( participant.getInning( 1 ).hasEnded() ).isTrue();
        assertThat( participant.getInning( 1 ).getFoul().isPresent() ).isFalse();
        assertThat( participant.getInning( 1 ).getPoints() ).isEqualTo( 5 );
    }

    @Test
    public void givenEventWhenProcessedThenInningAdvances() {
        assertThat( getParticipant( PLAYER_A ).getInning().getNumber() ).isEqualTo( 1 );

        game.processEvents( new StraightPoolEvent( 15 ), Optional.<Foul>absent() );

        assertThat( getParticipant( PLAYER_A ).getInning().getNumber() ).isEqualTo( 2 );
    }

    @Test
    @UseDataProvider( value = "provideZeroToFifteen", location = DataProviders.class )
    public void givenEventWithSafetysWhenProcessedThenControlPasses( int ballsLeft ) {
        game.processEvents( new StraightPoolEvent( ballsLeft )
            .withSafety(), Optional.<Foul>absent() );

        assertThat( game.getParticipants().getActiveParticipant() ).isEqualTo( getParticipant( PLAYER_B ) );
    }

    @Test
    @UseDataProvider( value = "provideZeroToFifteen", location = DataProviders.class )
    public void givenEventWithZeroOrOneBallsLeftThenControlDoesNotPass( int ballsLeft ) {
        game.processEvents( new StraightPoolEvent( ballsLeft ), Optional.<Foul>absent() );

        assertThat( game.getParticipants().getActiveParticipant() )
            .isEqualTo( getParticipant( ballsLeft < 2 ? PLAYER_A : PLAYER_B ) );
    }

    @Test
    @UseDataProvider( value = "provideZeroOne", location = DataProviders.class )
    public void givenPreviousFoulWhenProcessEventThenConsecutiveFoulsAreReset( int scoredPoints ) {
        StraightPoolParticipant participant = getParticipant( PLAYER_A );

        participant.increaseConsecutiveFouls();
        assertThat( participant.getConsecutiveFouls() ).isEqualTo( 1 );

        game.processEvents( new StraightPoolEvent( scoredPoints ), Optional.<Foul>absent() );

        assertThat( participant.getConsecutiveFouls() ).isEqualTo( 0 );
    }

    @Test
    public void givenEventWithMoreBallsThanOnTableWhenProcessedThenRejected() {
        game.processEvents( new StraightPoolEvent( 10 ), Optional.<Foul>absent() );

        thrown.expect( AssumptionException.class );
        game.processEvents( new StraightPoolEvent( 13 ), Optional.<Foul>absent() );
    }

}