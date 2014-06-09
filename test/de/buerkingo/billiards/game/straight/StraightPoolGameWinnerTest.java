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

    @Test
    public void givenParticipantWithHigherScoreWhenInningsLimitIsReachedThenParticipantWins() {
        game = createGame( 100, Optional.of( 2 ) );

        game.processEvents( new StraightPoolEvent( 10 ), Optional.<Foul>absent() );
        game.processEvents( new StraightPoolEvent( 10 ), Optional.<Foul>absent() );
        StraightPoolState state = game.processEvents( new StraightPoolEvent( 10 ), Optional.<Foul>absent() );

        assertThat( state.isGameOver() ).isFalse();

        state = game.processEvents( new StraightPoolEvent( 10 ), Optional.<Foul>absent() );

        assertThat( state.isGameOver() ).isTrue();
        assertThat( state.getWinner() ).isEqualTo( getParticipant( PLAYER_A ) );
    }

    @Test
    public void givenParticipantsWithEqualScoresWhenInningsLimitIsReachedThenGameContinuesUntilScored() {
        game = createGame( 100, Optional.of( 2 ) );

        game.processEvents( new StraightPoolEvent( 10 ), Optional.<Foul>absent() );
        game.processEvents( new StraightPoolEvent( 5 ), Optional.<Foul>absent() );
        game.processEvents( new StraightPoolEvent( 5 ), Optional.<Foul>absent() );
        StraightPoolState state = game.processEvents( new StraightPoolEvent( 5 ), Optional.<Foul>absent() );

        assertThat( state.isGameOver() ).isFalse();

        game.processEvents( new StraightPoolEvent( 5 ), Optional.<Foul>absent() );
        state = game.processEvents( new StraightPoolEvent( 5 ), Optional.<Foul>absent() );

        assertThat( state.isGameOver() ).isFalse();

        game.processEvents( new StraightPoolEvent( 5 ), Optional.<Foul>absent() );
        state = game.processEvents( new StraightPoolEvent( 4 ), Optional.<Foul>absent() );

        assertThat( state.isGameOver() ).isTrue();
        assertThat( state.getWinner() ).isEqualTo( getParticipant( PLAYER_B ) );
    }

}