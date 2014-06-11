package de.buerkingo.billiards.game.straight;

import java.util.Map;

import com.google.common.base.Optional;
import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.AfterStage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;

import de.buerkingo.billiards.game.straight.foul.Foul;
import de.buerkingo.billiards.util.reject.Reject;

public class WhenStraightPool<SELF extends WhenStraightPool<?>> extends Stage<SELF> {

    @ExpectedScenarioState
    public StraightPoolGame game;

    @ExpectedScenarioState
    public Map<String, StraightPoolParticipant> participants;

    private StraightPoolEvent event;

    public SELF $_misses_with_$_balls_left_on_the_table( String name, int balls ) {
        event = new StraightPoolEvent( balls );
        return self();
    }

    @AfterStage
    public void processEvent() {
        Reject.ifNull( event );
        game.processEvents( event, Optional.<Foul>absent() );
    }

}