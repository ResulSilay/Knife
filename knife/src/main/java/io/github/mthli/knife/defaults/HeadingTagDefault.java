package io.github.mthli.knife.defaults;

import java.util.HashMap;
import java.util.Map;

public enum HeadingTagDefault {
    H1(6f),
    H2(5f),
    H3(4f),
    H4(3f),
    H5(2f),
    H6(1f);

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