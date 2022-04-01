package io.github.mthli.knife.defaults;

import java.util.HashMap;
import java.util.Map;

public enum HeadingTagDefault {
    H1(6.7f),
    H2(5.7f),
    H3(4.7f),
    H4(3.7f),
    H5(2.7f),
    H6(1.7f);

    float value;

    private static final Map<Float, HeadingTagDefault> lookup = new HashMap<>();

    static {
        for (HeadingTagDefault headingTagDefault : HeadingTagDefault.values()) {
            lookup.put(headingTagDefault.getValue(), headingTagDefault);
        }
    }

    HeadingTagDefault(float value) {
        this.value = value;
    }

    public float getValue() {
        return value;
    }

    public static HeadingTagDefault get(float value) {
        return lookup.get(value);
    }
}