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
    @UseDataProvider( value = "provideTwoToFifteen", location = DataProviders.class )
    public void control_passes_after_a_shot_if_more_than_one_ball_is_left( int ballsLeftOnTableAfterInning ) {
        given().a_straight_pool_game()
            .and().$_plays_against_$( JACK, JANE );

        when().$_misses_with_$_balls_left_on_the_table( JACK, ballsLeftOnTableAfterInning );

        then().control_passes_to( JANE );
    }

    @Test
    @UseDataProvider( value = "provideZeroOne", location = DataProviders.class )
    public void control_does_not_pass_after_a_shot_if_less_than_two_balls_are_left( int ballsLeftOnTableAfterInning ) {
        given().a_straight_pool_game()
            .and().$_plays_against_$( JACK, JANE );

        when().$_misses_with_$_balls_left_on_the_table( JACK, ballsLeftOnTableAfterInning );

        then().$_is_still_at_the_table( JACK );
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

    @Test
    public void consecutive_fouls_are_increased_if_a_foul_occured() {
        given().a_straight_pool_game()
            .and().$_plays_against_$( JACK, JANE );

        when().$_finishes_with_$_balls_left_on_the_table( JACK, 15 )
            .and().the_inning_ends_with_a_foul();

        then().$_has_$_consecutive_fouls( JACK, 1 )
            .and().$_gets_$_points_deducted( JACK, 1 )
            .and().control_passes_to( JANE );
    }

    @Test
    public void consecutive_fouls_are_reset_and_then_increased_if_a_foul_occured_after_making_some_points() {
        given().a_straight_pool_game()
            .and().$_plays_against_$( JACK, JANE )
            .and().$_has_$_consecutive_fouls_already( JACK, 2 );

        when().$_finishes_with_$_balls_left_on_the_table( JACK, 10 )
            .and().the_inning_ends_with_a_foul();

        then().$_has_$_consecutive_fouls( JACK, 1 )
            .and().$_gets_$_points_deducted( JACK, 1 )
            .and().control_passes_to( JANE );
    }

    @Test
    public void consecutive_fouls_are_reset_if_no_foul_occured() {
        given().a_straight_pool_game()
            .and().$_plays_against_$( JACK, JANE )
            .and().$_has_$_consecutive_fouls_already( JACK, 2 );

        when().$_misses_with_$_balls_left_on_the_table( JACK, 15 );

        then().$_has_$_consecutive_fouls( JACK, 0 )
            .and().$_gets_$_points_deducted( JACK, 0 )
            .and().control_passes_to( JANE );
    }

    @Test
    public void consecutive_fouls_are_reset_and_the_table_is_reracked_if_a_non_standard_foul_occured() {
        given().a_straight_pool_game()
            .and().$_plays_against_$( JACK, JANE )
            .and().there_are_$_balls_on_the_table( 10 )
            .and().$_has_$_consecutive_fouls_already( JACK, 2 );

        when().$_finishes_with_$_balls_left_on_the_table( JACK, 10 )
            .and().the_inning_ends_with_a_serious_foul();

        then().$_has_$_consecutive_fouls( JACK, 0 )
            .and().$_gets_$_points_deducted( JACK, 15 )
            .and().there_are_$_balls_on_the_table( 15 )
            .and().control_passes_to( JANE );
    }

    @Test
    public void three_consecutive_fouls_lead_to_the_three_foul_rule_being_applied() {
        given().a_straight_pool_game()
            .and().$_plays_against_$( JACK, JANE )
            .and().there_are_$_balls_on_the_table( 10 )
            .and().$_has_$_consecutive_fouls_already( JACK, 2 );

        when().$_finishes_with_$_balls_left_on_the_table( JACK, 10 )
            .and().the_inning_ends_with_a_foul();

        then().$_has_$_consecutive_fouls( JACK, 0 )
            .and().$_gets_$_points_deducted( JACK, 16 )
            .and().there_are_$_balls_on_the_table( 15 )
            .and().control_passes_to( JANE );
    }

    @Test
    public void the_game_ends_when_a_player_reaches_the_point_limit() {
        given().a_straight_pool_game_with_a_$_points_limit( 10 )
            .and().$_plays_against_$( JACK, JANE );

        when().$_finishes_with_$_balls_left_on_the_table( JACK, 5 );

        then().$_wins_the_game( JACK );
    }

    @Test
    public void the_game_ends_when_a_player_overshoots_the_point_limit() {
        given().a_straight_pool_game_with_a_$_points_limit( 10 )
            .and().$_plays_against_$( JACK, JANE );

        when().$_finishes_with_$_balls_left_on_the_table( JACK, 3 );

        then().$_wins_the_game( JACK );
    }

    @Test
    public void the_game_does_not_end_until_the_inning_is_finished() {
        given().a_straight_pool_game_with_a_$_points_limit( 5 )
            .and().$_plays_against_$( JACK, JANE );

        when().$_finishes_with_$_balls_left_on_the_table( JACK, 1 );
        then().the_game_is_not_over();

        when().$_finishes_with_$_balls_left_on_the_table( JACK, 15 ).processEvent();
        then().$_wins_the_game( JACK );
    }

    @Test
    public void the_game_ends_if_the_innings_limit_is_reached() {
        given().a_straight_pool_game_with_a_$_points_limit_and_at_most_$_innings( 60, 2 )
            .and().$_plays_against_$( JACK, JANE );

        when().$_finishes_with_$_balls_left_on_the_table( JACK, 10 ).processEvent()
            .and().$_finishes_with_$_balls_left_on_the_table( JANE, 10 ).processEvent()
            .and().$_finishes_with_$_balls_left_on_the_table( JACK, 10 );
        then().$_has_$_points( JACK, 5 )
            .and().$_has_$_points( JANE, 0 )
            .and().the_game_is_not_over();

        when().$_finishes_with_$_balls_left_on_the_table( JANE, 10 ).processEvent();
        then().$_wins_the_game( JACK );
    }

    @Test
    public void the_game_does_not_end_upon_reaching_the_innings_limit_until_one_player_has_more_points() {
        given().a_straight_pool_game_with_a_$_points_limit_and_at_most_$_innings_and_$_extension_innings( 60, 2, 1 )
            .and().$_plays_against_$( JACK, JANE );

        when().$_finishes_with_$_balls_left_on_the_table( JACK, 10 ).processEvent()
            .and().$_finishes_with_$_balls_left_on_the_table( JANE, 5 ).processEvent()
            .and().$_finishes_with_$_balls_left_on_the_table( JACK, 5 ).processEvent()
            .and().$_finishes_with_$_balls_left_on_the_table( JANE, 5 );
        then().$_has_$_points( JACK, 5 )
            .and().$_has_$_points( JANE, 5 )
            .and().the_game_is_not_over();

        when().$_finishes_with_$_balls_left_on_the_table( JACK, 5 ).processEvent()
            .and().$_finishes_with_$_balls_left_on_the_table( JANE, 5 ).processEvent();
        then().the_game_is_not_over();

        when().$_finishes_with_$_balls_left_on_the_table( JACK, 5 ).processEvent()
            .and().$_finishes_with_$_balls_left_on_the_table( JANE, 4 ).processEvent();
        then().$_has_$_points( JACK, 5 )
            .and().$_has_$_points( JANE, 6 )
            .and().$_wins_the_game( JANE );
    }

    @Test
    public void the_game_does_not_end_upon_reaching_the_innings_limit_until_the_number_of_extension_innings_is_played() {
        given().a_straight_pool_game_with_a_$_points_limit_and_at_most_$_innings_and_$_extension_innings( 60, 1, 2 )
            .and().$_plays_against_$( JACK, JANE );

        when().$_finishes_with_$_balls_left_on_the_table( JACK, 10 ).processEvent()
            .and().$_finishes_with_$_balls_left_on_the_table( JANE, 5 ).processEvent()
            .and().$_finishes_with_$_balls_left_on_the_table( JACK, 5 ).processEvent()
            .and().$_finishes_with_$_balls_left_on_the_table( JANE, 4 ).processEvent()
            .and().$_finishes_with_$_balls_left_on_the_table( JACK, 4 ).processEvent()
            .and().$_finishes_with_$_balls_left_on_the_table( JANE, 4 );
        then().$_has_$_points( JACK, 5 )
            .and().$_has_$_points( JANE, 6 )
            .and().$_wins_the_game( JANE );
    }

    @Test
    public void a_player_can_win_a_game_during_extension_even_if_they_lagged_behind() {
        given().a_straight_pool_game_with_a_$_points_limit_and_at_most_$_innings_and_$_extension_innings( 60, 1, 2 )
            .and().$_plays_against_$( JACK, JANE );

        when().$_finishes_with_$_balls_left_on_the_table( JACK, 10 ).processEvent()
            .and().$_finishes_with_$_balls_left_on_the_table( JANE, 5 ).processEvent()
            .and().$_finishes_with_$_balls_left_on_the_table( JACK, 5 ).processEvent()
            .and().$_finishes_with_$_balls_left_on_the_table( JANE, 4 ).processEvent()
            .and().$_finishes_with_$_balls_left_on_the_table( JACK, 2 ).processEvent()
            .and().$_finishes_with_$_balls_left_on_the_table( JANE, 2 );
        then().$_has_$_points( JACK, 7 )
            .and().$_has_$_points( JANE, 6 )
            .and().$_wins_the_game( JACK );
    }

}