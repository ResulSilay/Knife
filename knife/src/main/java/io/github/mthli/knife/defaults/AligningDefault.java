package io.github.mthli.knife.defaults;

import java.util.HashMap;
import java.util.Map;

public enum AligningDefault {
    LEFT(0),
    RIGHT(1),
    CENTER(2),
    JUSTIFY(3);

    int value;

    private static final Map<Integer, AligningDefault> lookup = new HashMap<>();

    static {
        for (AligningDefault aligningDefault : AligningDefault.values()) {
            lookup.put(aligningDefault.getValue(), aligningDefault);
        }
    }

    AligningDefault(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static AligningDefault get(int value) {
        return lookup.get(value);
    }
}