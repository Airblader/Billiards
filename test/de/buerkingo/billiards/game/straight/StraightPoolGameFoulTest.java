package de.buerkingo.billiards.game.straight;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;

import de.buerkingo.billiards.game.straight.foul.StandardFoul;
import de.buerkingo.billiards.util.DataProviders;

@RunWith( DataProviderRunner.class )
public class StraightPoolGameFoulTest extends StraightPoolGameTestBase {

    @Test
    public void givenParticipantWhenParticipantFoulsThenConsecutiveFoulsAreIncreased() {
        assertThatParticipantHasConsecutiveFouls( PLAYER_A, 0 );

        game.processEvent( new StraightPoolEvent()
            .withNumberOfBallsLeftInRack( 15 )
            .withFoul( new StandardFoul() ) );

        assertThatParticipantHasConsecutiveFouls( PLAYER_A, 1 );
    }

    @Test
    public void givenParticipantWithPreviousFoulWhenParticipantDoesNotScoreConsecutiveFoulsAreReset() {
        game.getParticipants().getActiveParticipant().increaseConsecutiveFouls();

        assertThatParticipantHasConsecutiveFouls( PLAYER_A, 1 );

        game.processEvent( new StraightPoolEvent()
            .withNumberOfBallsLeftInRack( 15 ) );

        assertThatParticipantHasConsecutiveFouls( PLAYER_A, 0 );
    }

    @Test
    public void givenParticipantWithPreviousFoulWhenParticipantScoresThenConsecutiveFoulsAreReset() {
        game.getParticipants().getActiveParticipant().increaseConsecutiveFouls();
        game.getParticipants().getActiveParticipant().increaseConsecutiveFouls();

        assertThatParticipantHasConsecutiveFouls( PLAYER_A, 2 );

        game.processEvent( new StraightPoolEvent()
            .withNumberOfBallsLeftInRack( 10 )
            .withFoul( new StandardFoul() ) );

        assertThatParticipantHasConsecutiveFouls( PLAYER_A, 1 );
    }

    @Test
    @UseDataProvider( value = "provideTrueFalse", location = DataProviders.class )
    public void givenParticipantWhenParticipantFoulsThenPenaltyIsConducted( boolean isFirstShot ) {
        if( !isFirstShot ) {
            game.processEvent( new StraightPoolEvent()
                .withNumberOfBallsLeftInRack( 15 ) );

            game.processEvent( new StraightPoolEvent()
                .withNumberOfBallsLeftInRack( 15 ) );
        }

        game.processEvent( new StraightPoolEvent()
            .withNumberOfBallsLeftInRack( 15 )
            .withFoul( new StandardFoul() ) );

        assertThat( getParticipant( PLAYER_A ).getPoints() ).isEqualTo( isFirstShot ? -2 : -1 );
    }

    @Test
    @UseDataProvider( value = "provideTrueFalse", location = DataProviders.class )
    public void givenParticipantWithTwoPreviousFoulsWhenParticipantFoulsThenPenaltyIsConducted( boolean isFirstShot ) {
        if( !isFirstShot ) {
            game.processEvent( new StraightPoolEvent()
                .withNumberOfBallsLeftInRack( 15 ) );

            game.processEvent( new StraightPoolEvent()
                .withNumberOfBallsLeftInRack( 15 ) );
        }

        game.getParticipants().getActiveParticipant().increaseConsecutiveFouls();
        game.getParticipants().getActiveParticipant().increaseConsecutiveFouls();

        game.processEvent( new StraightPoolEvent()
            .withNumberOfBallsLeftInRack( 15 )
            .withFoul( new StandardFoul() ) );

        assertThat( getParticipant( PLAYER_A ).getPoints() ).isEqualTo( isFirstShot ? -17 : -16 );
    }

    private void assertThatParticipantHasConsecutiveFouls( String name, int numberOfFouls ) {
        assertThat( getParticipant( name ).getConsecutiveFouls() ).isEqualTo( numberOfFouls );
    }

}