package io.github.mthli.knife.type;

import java.util.HashMap;
import java.util.Map;

public enum MediaImageType {
    URI(0),
    FILE(1),
    URL(2);

    int value;

    private static final Map<Integer, MediaImageType> lookup = new HashMap<>();

    static {
        for (MediaImageType mediaImageType : MediaImageType.values()) {
            lookup.put(mediaImageType.getValue(), mediaImageType);
        }
    }

    MediaImageType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static MediaImageType get(int value) {
        return lookup.get(value);
    }
}