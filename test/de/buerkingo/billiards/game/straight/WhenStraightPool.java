package de.buerkingo.billiards.game.straight;

import java.util.Map;

import com.google.common.base.Optional;
import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.AfterStage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import com.tngtech.jgiven.annotation.Hidden;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;

import de.buerkingo.billiards.game.straight.foul.Foul;
import de.buerkingo.billiards.game.straight.foul.SeriousFoul;
import de.buerkingo.billiards.game.straight.foul.StandardFoul;
import de.buerkingo.billiards.util.reject.Reject;

public class WhenStraightPool<SELF extends WhenStraightPool<?>> extends Stage<SELF> {

    @ExpectedScenarioState
    public StraightPoolGame game;

    @ExpectedScenarioState
    public Map<String, StraightPoolParticipant> participants;

    @ProvidedScenarioState
    public StraightPoolState state;

    private StraightPoolEvent event;
    private Optional<? extends Foul> foul = Optional.absent();

    public SELF $_misses_with_$_balls_left_on_the_table( String name, int balls ) {
        return $_finishes_with_$_balls_left_on_the_table( name, balls );
    }

    public SELF $_finishes_with_$_balls_left_on_the_table( String name, int balls ) {
        event = new StraightPoolEvent( balls );
        return self();
    }

    public SELF the_inning_ends_with_a_foul() {
        foul = Optional.of( new StandardFoul() );
        return self();
    }

    public SELF the_inning_ends_with_a_serious_foul() {
        foul = Optional.of( SeriousFoul.unsportsmanlikeConduct() );
        return self();
    }

    public SELF the_inning_ends_with_a_safety() {
        rejectIfEventHasNotBeenCreated();
        event.withSafety();

        return self();
    }

    @AfterStage
    @Hidden
    public SELF processEvent() {
        rejectIfEventHasNotBeenCreated();
        state = game.processEvents( event, foul );

        return self();
    }

    private void rejectIfEventHasNotBeenCreated() {
        Reject.ifNull( event );
    }

}