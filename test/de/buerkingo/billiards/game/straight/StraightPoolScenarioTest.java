package de.buerkingo.billiards.game.straight;

import org.junit.Test;

import com.tngtech.jgiven.base.ScenarioTestBase;

public class StraightPoolScenarioTest extends ScenarioTestBase<GivenStraightPool<?>, WhenStraightPool<?>, ThenStraightPool<?>> {

    @Test
    public void foo() {
        given().a_straight_pool_game();
        when().nothing_is_done();
        then().nothing_happened();
    }

}