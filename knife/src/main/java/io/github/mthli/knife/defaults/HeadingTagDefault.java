package io.github.mthli.knife.defaults;

public enum HeadingTagDefault {
    H1(6.7f),
    H2(5.7f),
    H3(4.7f),
    H4(3.7f),
    H5(2.7f),
    H6(1.7f);

    float value;

    HeadingTagDefault(float value) {
        this.value = value;
    }

    public float getValue() {
        return value;
    }
}