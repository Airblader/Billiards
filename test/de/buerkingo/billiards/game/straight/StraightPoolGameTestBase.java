package de.buerkingo.billiards.game.straight;

import org.junit.Rule;
import org.junit.rules.ExpectedException;

import com.google.common.base.Optional;

import de.buerkingo.billiards.game.straight.StraightPoolGame.Builder;
import de.buerkingo.billiards.participants.Person;
import de.buerkingo.billiards.util.reject.Reject;

public class StraightPoolGameTestBase {

    public static final String PLAYER_A = "Player A";
    public static final String PLAYER_B = "Player B";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    public StraightPoolGame game;

    public StraightPoolGame createGame( int pointsToWin, Optional<Integer> maxInnings ) {
        Builder gameBuilder = StraightPoolGame.builder()
            .withPointsToWin( pointsToWin );
        if( maxInnings.isPresent() ) {
            gameBuilder.withInningsLimit( maxInnings.get(), Optional.<Integer>absent() );
        }

        StraightPoolGame game = gameBuilder.get();
        game.getParticipants().setParticipants( createParticipant( PLAYER_A ), createParticipant( PLAYER_B ) );

        return game;
    }

    public StraightPoolParticipant createParticipant( String name ) {
        return new StraightPoolParticipant( new Person( name ) );
    }

    public StraightPoolParticipant getParticipant( String name ) {
        Reject.ifNull( game );
        for( int i = 0; i < game.getParticipants().getNumberOfParticipants(); i++ ) {
            StraightPoolParticipant currentParticipant = game.getParticipants().get( i );

            if( currentParticipant.getIdentity().getName().equals( name ) ) {
                return currentParticipant;
            }
        }

        throw Reject.always( "participant not found" );
    }

}
