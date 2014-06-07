package de.buerkingo.billiards.game.straight;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;

import de.buerkingo.billiards.game.straight.foul.SimpleFoul;
import de.buerkingo.billiards.util.DataProviders;

@RunWith( DataProviderRunner.class )
public class StraightPoolGameFoulTest extends StraightPoolGameTestBase {

    @Test
    public void givenParticipantWhenParticipantFoulsThenConsecutiveFoulsAreIncreased() {
        assertThatParticipantHasConsecutiveFouls( PLAYER_A, 0 );

        game.processEvent( new StraightPoolEvent()
            .withFoul( new SimpleFoul() ) );

        assertThatParticipantHasConsecutiveFouls( PLAYER_A, 1 );
    }

    @Test
    @UseDataProvider( value = "provideTrueFalse", location = DataProviders.class )
    public void givenParticipantWithPreviousFoulWhenParticipantScoresThenConsecutiveFoulsAreReset( boolean inningEndsWithFoul ) {
        game.getParticipants().getActiveParticipant().increaseConsecutiveFouls();
        assertThatParticipantHasConsecutiveFouls( PLAYER_A, 1 );

        game.processEvent( new StraightPoolEvent()
            .withNumberOfBallsLeftInRack( 10 )
            .withFoul( inningEndsWithFoul ? new SimpleFoul() : null ) );

        assertThatParticipantHasConsecutiveFouls( PLAYER_A, 0 );
    }

    private void assertThatParticipantHasConsecutiveFouls( String name, int numberOfFouls ) {
        assertThat( getParticipant( name ).getConsecutiveFouls() ).isEqualTo( numberOfFouls );
    }

}