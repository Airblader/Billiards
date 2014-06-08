package de.buerkingo.billiards.game.straight.foul;

// http://www.wpa-pool.com/web/index.asp?id=119&pagetype=rules#4.10
public enum FoulReason {
    UNCATEGORIZED,

    SCRATCHED,
    BALL_OFF_THE_TABLE,
    NO_RAIL_AFTER_CONTACT,
    NO_FOOT_ON_THE_FLOOR,
    TOUCHED_BALL,
    DOUBLE_HIT,
    PUSH_SHOT,
    BALLS_STILL_MOVING,
    SLOW_PLAY;

    // TODO use this to determine points
}