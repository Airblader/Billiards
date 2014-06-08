package de.buerkingo.billiards.game.straight;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.tngtech.java.junit.dataprovider.DataProviderRunner;

import de.buerkingo.billiards.game.straight.foul.SimpleFoul;

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
            .withFoul( new SimpleFoul() ) );

        assertThatParticipantHasConsecutiveFouls( PLAYER_A, 1 );
    }

    private void assertThatParticipantHasConsecutiveFouls( String name, int numberOfFouls ) {
        assertThat( getParticipant( name ).getConsecutiveFouls() ).isEqualTo( numberOfFouls );
    }

}