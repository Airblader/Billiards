package de.buerkingo.billiards.game.straight;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;

import com.google.common.base.Optional;

import de.buerkingo.billiards.game.straight.foul.Foul;

public class StraightPoolGameWinnerTest extends StraightPoolGameTestBase {

    @Test
    public void givenParticipantReachesPointsToWinExactlyWhenProcessThenParticipantWins() {
        game = createGame( 5, Optional.<Integer>absent() );
        StraightPoolState state = game.processEvents( new StraightPoolEvent( 10 ), Optional.<Foul>absent() );

        assertThat( state.isGameOver() ).isTrue();
        assertThat( state.getWinner() ).isEqualTo( getParticipant( PLAYER_A ) );
    }

    @Test
    public void givenParticipantReachesMorePointsThanPointsToWinWhenProcessThenParticipantWins() {
        game = createGame( 5, Optional.<Integer>absent() );
        StraightPoolState state = game.processEvents( new StraightPoolEvent( 5 ), Optional.<Foul>absent() );

        assertThat( state.isGameOver() ).isTrue();
        assertThat( state.getWinner() ).isEqualTo( getParticipant( PLAYER_A ) );
    }

    /*
     * TODO Missing test
     * Given participant
     * When participant shoots out rack and reaches points to win in doing so
     * Then the participant can still finish his inning.
     */

}