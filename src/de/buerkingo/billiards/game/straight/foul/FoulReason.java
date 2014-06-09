package de.buerkingo.billiards.game.straight.foul;

import com.google.common.collect.ImmutableList;

public enum FoulReason {
    UNCATEGORIZED,

    CUE_BALL_SCRATCHED_OR_OFF_THE_TABLE,
    NO_RAIL_AFTER_CONTACT,
    NO_FOOT_ON_FLOOR,
    BALL_DRIVEN_OFF_THE_TABLE,
    TOUCHED_BALL,
    DOUBLE_HIT,
    PUSH_SHOT,
    BALLS_STILL_MOVING,
    BAD_CUE_BALL_PLACEMENT,
    BAD_PLAY_FROM_BEHIND_THE_HEAD_STRING,
    CUE_STICK_ON_TABLE,
    PLAYING_OUT_OF_TURN,
    SLOW_PLAY,

    BREAK_FOUL,
    CONSECUTIVE_FOULS,
    UNSPORTSMANLIKE_CONDUCT;

    private static final ImmutableList<FoulReason> STANDARD_FOULS = ImmutableList.of(
        CUE_BALL_SCRATCHED_OR_OFF_THE_TABLE,
        NO_RAIL_AFTER_CONTACT,
        NO_FOOT_ON_FLOOR,
        BALL_DRIVEN_OFF_THE_TABLE,
        TOUCHED_BALL,
        DOUBLE_HIT,
        PUSH_SHOT,
        BALLS_STILL_MOVING,
        BAD_CUE_BALL_PLACEMENT,
        BAD_PLAY_FROM_BEHIND_THE_HEAD_STRING,
        CUE_STICK_ON_TABLE,
        PLAYING_OUT_OF_TURN,
        SLOW_PLAY );

    public boolean isStandardFoul() {
        return this == UNCATEGORIZED || STANDARD_FOULS.contains( this );
    }

    public boolean countsAsFoul() {
        return isStandardFoul();
    }

}