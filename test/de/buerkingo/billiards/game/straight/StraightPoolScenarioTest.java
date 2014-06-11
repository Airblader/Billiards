package de.buerkingo.billiards.game.straight;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import com.tngtech.jgiven.junit.ScenarioTest;

import de.buerkingo.billiards.util.DataProviders;

@RunWith( DataProviderRunner.class )
public class StraightPoolScenarioTest extends ScenarioTest<GivenStraightPool<?>, WhenStraightPool<?>, ThenStraightPool<?>> {

    private static final String JACK = "Jack";
    private static final String JANE = "Jane";

    @Test
    public void control_passes_after_a_shot() {
        given().a_straight_pool_game()
            .and().$_plays_against_$( JACK, JANE );

        when().$_misses_with_$_balls_left_on_the_table( JACK, 15 );

        then().control_passes_to( JANE );
    }

    @Test
    public void an_inning_with_some_scored_points_is_processed_correctly() {
        given().a_straight_pool_game()
            .and().$_plays_against_$( JACK, JANE );

        when().$_misses_with_$_balls_left_on_the_table( JACK, 10 );

        then().$_has_$_points( JACK, 5 )
            .and().$_has_no_fouls( JACK )
            .and().$_has_$_consecutive_fouls( JACK, 0 )
            .and().the_inning_is_over_for( JACK )
            .and().there_are_$_balls_on_the_table( 10 )
            .and().control_passes_to( JANE );
    }

    @Test
    @UseDataProvider( value = "provideZeroToFifteen", location = DataProviders.class )
    public void a_safety_always_causes_the_control_to_pass( int ballsLeftOnTableAfterInning ) {
        given().a_straight_pool_game()
            .and().$_plays_against_$( JACK, JANE );

        when().$_misses_with_$_balls_left_on_the_table( JACK, ballsLeftOnTableAfterInning )
            .and().the_inning_ends_with_a_safety();

        then().control_passes_to( JANE );
    }

}