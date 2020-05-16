package com.nanachi.headfirst.designpattern.observer.simpleversion;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum State {
    NEW((byte) 0), DELETE((byte) 1), UPDATE((byte) 2), ALTER((byte) 3);

    private static final Map<Byte, State> STATE_MAP = Collections.unmodifiableMap(
            Arrays.stream(values()).collect(Collectors.toMap(State::state, Function.identity()))
    );

    private final byte state;

    State(final byte state) {
        this.state = state;
    }

    public byte state() {
        return state;
    }

    public static State getState(final byte state) {
        return STATE_MAP.get(state);
    }
}
