package de.buerkingo.billiards.game.straight;

import static com.google.common.collect.Maps.newHashMap;

import java.util.Map;

import com.google.common.base.Optional;
import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;

import de.buerkingo.billiards.game.straight.StraightPoolGame.Builder;
import de.buerkingo.billiards.participants.Person;
import de.buerkingo.billiards.util.reject.Reject;

public class GivenStraightPool<SELF extends GivenStraightPool<?>> extends Stage<SELF> {

    @ProvidedScenarioState
    public StraightPoolGame game;

    @ProvidedScenarioState
    public Map<String, StraightPoolParticipant> participants = newHashMap();

    public SELF a_straight_pool_game() {
        return a_straight_pool_game_with_a_$_points_limit_and_at_most_$_innings( Optional.<Integer>absent(), Optional.<Integer>absent() );
    }

    public SELF a_straight_pool_game_with_a_$_points_limit( int pointsToWin ) {
        return a_straight_pool_game_with_a_$_points_limit_and_at_most_$_innings( Optional.of( pointsToWin ), Optional.<Integer>absent() );
    }

    public SELF a_straight_pool_game_with_a_$_points_limit_and_at_most_$_innings( Optional<Integer> pointsToWin, Optional<Integer> maxInnings ) {
        Builder gameBuilder = StraightPoolGame.builder()
            .withPointsToWin( pointsToWin.or( 60 ) );
        if( maxInnings.isPresent() ) {
            gameBuilder.withMaxInnings( maxInnings.get() );
        }

        game = gameBuilder.get();
        return self();
    }

    public SELF $_plays_against_$( String namePlayerA, String namePlayerB ) {
        rejectIfGameHasNotBeenCreated();
        game.getParticipants().setParticipants(
            createParticipant( namePlayerA ),
            createParticipant( namePlayerB ) );

        return self();
    }

    public SELF $_has_$_consecutive_fouls_already( String name, int fouls ) {
        for( int i = 1; i <= fouls; i++ ) {
            participants.get( name ).increaseConsecutiveFouls();
        }

        return self();
    }

    public SELF there_are_$_balls_on_the_table( int balls ) {
        rejectIfGameHasNotBeenCreated();
        game.getRack().setCurrentNumberOfBalls( balls );

        return self();
    }

    private StraightPoolParticipant createParticipant( String name ) {
        StraightPoolParticipant participant = new StraightPoolParticipant( new Person( name ) );
        participants.put( name, participant );
        return participant;
    }

    private void rejectIfGameHasNotBeenCreated() {
        Reject.ifNull( game );
    }

}