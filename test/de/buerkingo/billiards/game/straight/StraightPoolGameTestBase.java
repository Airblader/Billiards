package de.buerkingo.billiards.game.straight;

import org.junit.Before;

import de.buerkingo.billiards.participants.Person;
import de.buerkingo.billiards.util.reject.Reject;

public class StraightPoolGameTestBase {

    public static final String PLAYER_A = "Player A";
    public static final String PLAYER_B = "Player B";

    public StraightPoolGame game;

    @Before
    public void before() {
        createGame();
    }

    public void createGame() {
        createGame( 60 );
    }

    public void createGame( int pointsLimit ) {
        game = StraightPoolGame.builder()
            .withPointsToWin( pointsLimit )
            .get();

        createParticipantsAndAddToGame();
    }

    private void createParticipantsAndAddToGame() {
        game.getParticipants().setParticipants( createParticipant( PLAYER_A ), createParticipant( PLAYER_B ) );
    }

    public StraightPoolParticipant createParticipant( String name ) {
        return new StraightPoolParticipant( new Person( name ) );
    }

    public StraightPoolParticipant getParticipant( String name ) {
        for( int i = 0; i < game.getParticipants().getNumberOfParticipants(); i++ ) {
            StraightPoolParticipant participant = game.getParticipants().get( i );

            if( name.equals( participant.getIdentity().getName() ) ) {
                return participant;
            }
        }

        throw Reject.always( "Participant not found" );
    }

}
