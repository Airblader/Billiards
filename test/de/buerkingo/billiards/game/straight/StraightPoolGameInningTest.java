package de.buerkingo.billiards.game.straight;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;

import com.google.common.base.Optional;

import de.buerkingo.billiards.game.straight.events.FinishedInningEvent;
import de.buerkingo.billiards.game.straight.foul.Foul;

public class StraightPoolGameInningTest extends StraightPoolGameTestBase {

    @Test
    public void givenFinishedInningWithNoPointsWhenProcessedThenControlPasses() {
        assertThat( game.getParticipants().getActiveParticipant() ).isEqualTo( getParticipant( PLAYER_A ) );

        game.processEvents( new FinishedInningEvent( 15 ), Optional.<Foul>absent() );

        assertThat( game.getParticipants().getActiveParticipant() ).isEqualTo( getParticipant( PLAYER_B ) );
        assertThat( getParticipant( PLAYER_A ).getInning().getNumber() ).isEqualTo( 2 );
    }

    @Test
    public void givenFinishedInningWithScoredPointsWhenProcessedThenInningIsProcessedCorrectly() {
        game.processEvents( new FinishedInningEvent( 10 ), Optional.<Foul>absent() );

        StraightPoolParticipant participant = getParticipant( PLAYER_A );
        assertThat( participant.getConsecutiveFouls() ).isEqualTo( 0 );
        assertThat( participant.getInning().getNumber() ).isEqualTo( 2 );
        assertThat( participant.getInning( 1 ).getFoul().isPresent() ).isFalse();
        assertThat( participant.getInning( 1 ).getPoints() ).isEqualTo( 5 );
    }

}