package de.buerkingo.billiards.game.straight;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.common.base.Optional;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;

import de.buerkingo.billiards.game.straight.foul.Foul;
import de.buerkingo.billiards.game.straight.foul.SeriousFoul;
import de.buerkingo.billiards.game.straight.foul.StandardFoul;
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

    @Test
    public void givenStandardFoulWhenProcessEventThenProcessedCorrectly() {
        game.processEvents( new StraightPoolEvent( 10 ), Optional.of( new StandardFoul() ) );

        StraightPoolParticipant participant = getParticipant( PLAYER_A );
        assertThat( participant.getConsecutiveFouls() ).isEqualTo( 1 );
        assertThat( participant.getPoints() ).isEqualTo( 4 );

        assertThat( game.getParticipants().getActiveParticipant() ).isEqualTo( getParticipant( PLAYER_B ) );
    }

    @Test
    public void givenPreviousFoulWhenProcessEventWithPointsAndStandardFoulThenProcessedCorrectly() {
        StraightPoolParticipant participant = getParticipant( PLAYER_A );
        participant.increaseConsecutiveFouls();

        game.processEvents( new StraightPoolEvent( 10 ), Optional.of( new StandardFoul() ) );

        assertThat( participant.getConsecutiveFouls() ).isEqualTo( 1 );
        assertThat( participant.getPoints() ).isEqualTo( 4 );

        assertThat( game.getParticipants().getActiveParticipant() ).isEqualTo( getParticipant( PLAYER_B ) );
    }

    @Test
    public void givenPreviousFoulWhenProcessEventWithoutPointsAndStandardFoulThenConsecutiveFoulsIncrease() {
        StraightPoolParticipant participant = getParticipant( PLAYER_A );
        participant.increaseConsecutiveFouls();

        game.processEvents( new StraightPoolEvent( 15 ), Optional.of( new StandardFoul() ) );

        assertThat( participant.getConsecutiveFouls() ).isEqualTo( 2 );
        assertThat( participant.getPoints() ).isEqualTo( -1 );

        assertThat( game.getParticipants().getActiveParticipant() ).isEqualTo( getParticipant( PLAYER_B ) );
    }

    @Test
    public void givenPreviousFoulWhenProcessEventWithNonStandardFoulThenConsecutiveFoulsAreReset() {
        StraightPoolParticipant participant = getParticipant( PLAYER_A );
        participant.increaseConsecutiveFouls();
        game.getRack().setCurrentNumberOfBalls( 10 );

        game.processEvents( new StraightPoolEvent( 10 ), Optional.of( SeriousFoul.unsportsmanlikeConduct() ) );

        assertThat( participant.getConsecutiveFouls() ).isEqualTo( 0 );
        assertThat( participant.getPoints() ).isEqualTo( -15 );

        assertThat( game.getParticipants().getActiveParticipant() ).isEqualTo( getParticipant( PLAYER_B ) );

        assertThat( game.getRack().getCurrentNumberOfBalls() ).isEqualTo( 15 );
    }

    @Test
    public void givenTwoPreviousFoulsWhenProcessEventWithStandardFoulThenConsecutiveFoulsRuleApplies() {
        StraightPoolParticipant participant = getParticipant( PLAYER_A );

        participant.increaseConsecutiveFouls();
        participant.increaseConsecutiveFouls();
        game.getRack().setCurrentNumberOfBalls( 10 );

        game.processEvents( new StraightPoolEvent( 10 ), Optional.of( new StandardFoul() ) );

        assertThat( participant.getConsecutiveFouls() ).isEqualTo( 0 );
        assertThat( participant.getPoints() ).isEqualTo( -16 );

        assertThat( game.getParticipants().getActiveParticipant() ).isEqualTo( getParticipant( PLAYER_B ) );

        assertThat( game.getRack().getCurrentNumberOfBalls() ).isEqualTo( 15 );
    }

    @Test
    @UseDataProvider( value = "provideZeroToFifteen", location = DataProviders.class )
    public void givenEventWhenProcessedThenRackIsUpdates( int ballsLeft ) {
        game.processEvents( new StraightPoolEvent( ballsLeft ), Optional.<Foul>absent() );
        assertThat( game.getRack().getCurrentNumberOfBalls() ).isEqualTo( ballsLeft < 2 ? 15 : ballsLeft );
    }

}