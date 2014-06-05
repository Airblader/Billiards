package de.buerkingo.billiards.game.straight;

import org.junit.Test;

public class StraightPoolGameTest {

    @Test
    public void givenGameWhenEventIsProcessedThenControlPasses() {
        StraightPoolGame game = createGame();

        game.processEvent( new StraightPoolEvent() );

        // TODO add assertions
    }

    @Test
    public void givenGameWhenPlayerScoresNoPointsThenPointsAreUnchanged() {
        StraightPoolGame game = createGame();
        StraightPoolEvent event = new StraightPoolEvent()
            .withNumberOfBallsLeftInRack( StraightPoolRack.NUMBER_OF_BALLS );

        game.processEvent( event );

        // TODO add assertions
    }

    private StraightPoolGame createGame() {
        return new StraightPoolGame();
    }

}
