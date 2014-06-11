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

}