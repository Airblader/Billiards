package de.buerkingo.billiards.game.straight;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.common.base.Optional;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;

import de.buerkingo.billiards.game.straight.events.FinishedInningEvent;
import de.buerkingo.billiards.game.straight.foul.Foul;
import de.buerkingo.billiards.util.DataProviders;
import de.buerkingo.billiards.util.reject.AssumptionException;

@RunWith( DataProviderRunner.class )
public class StraightPoolGameInningTest extends StraightPoolGameTestBase {

    @Test
    public void givenFinishedInningWithScoredPointsWhenProcessedThenInningIsProcessedCorrectly() {
        game.processEvents( new FinishedInningEvent( 10 ), Optional.<Foul>absent() );

        StraightPoolParticipant participant = getParticipant( PLAYER_A );
        assertThat( participant.getConsecutiveFouls() ).isEqualTo( 0 );
        assertThat( participant.getInning().getNumber() ).isEqualTo( 2 );

        assertThat( participant.getInning( 1 ).hasEnded() ).isTrue();
        assertThat( participant.getInning( 1 ).getFoul().isPresent() ).isFalse();
        assertThat( participant.getInning( 1 ).getPoints() ).isEqualTo( 5 );
    }

    @Test
    public void givenFinishedInningWhenProcessedThenInningAdvances() {
        assertThat( getParticipant( PLAYER_A ).getInning().getNumber() ).isEqualTo( 1 );

        game.processEvents( new FinishedInningEvent( 15 ), Optional.<Foul>absent() );

        assertThat( getParticipant( PLAYER_A ).getInning().getNumber() ).isEqualTo( 2 );
    }

    @Test
    @UseDataProvider( value = "provideZeroToFifteen", location = DataProviders.class )
    public void givenFinishedInningWithSafetysWhenProcessedThenControlPasses( int ballsLeft ) {
        game.processEvents( new FinishedInningEvent( ballsLeft )
            .withSafety(), Optional.<Foul>absent() );

        assertThat( game.getParticipants().getActiveParticipant() ).isEqualTo( getParticipant( PLAYER_B ) );
    }

    @Test
    @UseDataProvider( value = "provideZeroToFifteen", location = DataProviders.class )
    public void givenFinishedInningWithZeroOrOneBallsLeftThenControlDoesNotPass( int ballsLeft ) {
        game.processEvents( new FinishedInningEvent( ballsLeft ), Optional.<Foul>absent() );

        assertThat( game.getParticipants().getActiveParticipant() )
            .isEqualTo( getParticipant( ballsLeft < 2 ? PLAYER_A : PLAYER_B ) );
    }

    @Test
    @UseDataProvider( value = "provideZeroOne", location = DataProviders.class )
    public void givenPreviousFoulWhenProcessFinishedInningThenConsecutiveFoulsAreReset( int scoredPoints ) {
        StraightPoolParticipant participant = getParticipant( PLAYER_A );

        participant.increaseConsecutiveFouls();
        assertThat( participant.getConsecutiveFouls() ).isEqualTo( 1 );

        game.processEvents( new FinishedInningEvent( scoredPoints ), Optional.<Foul>absent() );

        assertThat( participant.getConsecutiveFouls() ).isEqualTo( 0 );
    }

    @Test
    public void givenFinishedInningWithMoreBallsThanOnTableWhenProcessedThenRejected() {
        game.processEvents( new FinishedInningEvent( 10 ), Optional.<Foul>absent() );

        thrown.expect( AssumptionException.class );
        game.processEvents( new FinishedInningEvent( 13 ), Optional.<Foul>absent() );
    }

}