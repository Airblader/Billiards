package de.buerkingo.billiards.game.straight;

import static org.fest.assertions.Assertions.assertThat;

import java.util.Map;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;

public class ThenStraightPool<SELF extends ThenStraightPool<?>> extends Stage<SELF> {

    @ExpectedScenarioState
    public StraightPoolGame game;

    @ExpectedScenarioState
    public Map<String, StraightPoolParticipant> participants;

    public SELF control_passes_to( String name ) {
        assertThat( game.getParticipants().getActiveParticipant() ).isEqualTo( participants.get( name ) );
        return self();
    }

    public SELF $_has_$_points( String name, int points ) {
        assertThat( participants.get( name ).getPoints() ).isEqualTo( points );
        return self();
    }

    public SELF $_has_$_consecutive_fouls( String name, int consecutiveFouls ) {
        assertThat( participants.get( name ).getConsecutiveFouls() ).isEqualTo( consecutiveFouls );
        return self();
    }

    public SELF $_has_no_fouls( String name ) {
        $_gets_$_points_deducted( name, 0 );
        return self();
    }

    public SELF $_gets_$_points_deducted( String name, int foulPoints ) {
        assertThat( participants.get( name ).getLastInning().getFoulPoints() ).isEqualTo( foulPoints );
        return self();
    }

    public SELF there_are_$_balls_on_the_table( int balls ) {
        assertThat( game.getRack().getCurrentNumberOfBalls() ).isEqualTo( balls );
        return self();
    }

    public SELF the_inning_is_over_for( String name ) {
        assertThat( participants.get( name ).getLastInning().hasEnded() ).isTrue();
        return self();
    }

}