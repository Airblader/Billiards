package de.buerkingo.billiards.game.straight;

import org.junit.Test;

import com.tngtech.jgiven.junit.ScenarioTest;

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

}