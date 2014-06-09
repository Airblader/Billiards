package de.buerkingo.billiards.util;

import java.io.Serializable;

public class BooleanSwitch implements Serializable {

    private static final long serialVersionUID = 1L;

    public static class OnSwitch extends BooleanSwitch {
        private static final long serialVersionUID = 1L;

        private OnSwitch() {
            super( false );
        }

        public static OnSwitch off() {
            return new OnSwitch();
        }

        public boolean on() {
            return switchState();
        }
    }

    private boolean state;
    private boolean hasSwitched = false;

    public BooleanSwitch( boolean initialState ) {
        this.state = initialState;
    }

    public boolean get() {
        return state;
    }

    protected boolean switchState() {
        if( !hasSwitched ) {
            hasSwitched = true;
            state = !state;
        }

        return get();
    }

}